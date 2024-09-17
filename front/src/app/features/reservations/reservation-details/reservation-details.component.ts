import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ReservationService } from 'src/app/services/reservation.service';
import { Bus } from 'src/app/shared/models/bus.model';
import { IReservation, Reservation } from 'src/app/shared/models/reservation.model';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import { forkJoin, of, switchMap } from 'rxjs';
import { ReferenceService } from 'src/app/services/reference.service';
import { PaymentModalComponent } from 'src/app/shared/components/payment/payment-modal.component';
import { Reference } from 'src/app/shared/models/reference';
import { ITrajet } from 'src/app/shared/models/trajet.model';
import { ReservationSummaryComponent } from '../reservation-summary/reservation-summary.component';

@Component({
    selector: 'app-reservation-details',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        FormsModule,
        CommonModule,
        RouterModule
    ],
    templateUrl: './reservation-details.component.html',
    styleUrls: ['./reservation-details.component.scss']
})
export class ReservationDetailsComponent implements OnInit {
    reservationId = '';
    reservation: Reservation = new Reservation();
    form!: FormGroup;
    availableBuses: Bus[] = [];
    paymentType: Reference[] = [];
    tripIndexToDelete: number | null = null;

    constructor(
        private route: ActivatedRoute,
        private formbuilder: FormBuilder,
        private modalService: NgbModal,
        private referenceService: ReferenceService,
        private service: ReservationService) {
        this.form = this.formbuilder.group({
            clientId: [1],
            trips: this.formbuilder.array([]),
            totalPrice: [null, [Validators.required]]
        });
    }

    ngOnInit(): void {
        this.availableBuses = this.route.snapshot.data['busList'];
        if (this.route.snapshot.data && this.route.snapshot.data['reservation']) {
            this.reservationId = this.route.snapshot.paramMap.get('id') || '';
            this.reservation = this.route.snapshot.data['reservation'];
            this.form.patchValue({
                clientId: this.reservation.clientId,
                id: this.reservation.id,
                totalPrice: this.reservation.totalPrice,
            });
            (this.reservation.trips || []).forEach(t => (this.form.get('trips') as FormArray).push(this.newTrip(t)));
        } else {
            this.reservation.clientId = 1;
        }

        this.referenceService.getPaymentType().subscribe({
            next: (data: Reference[]) => {
                this.paymentType = data;
            },
            error: (err: any) => {
                console.log(JSON.stringify(err));
                this.paymentType = [];
            }
        });
    }

    trips(): FormArray {
        return this.form.get('trips') as FormArray;
    }

    newTrip(trip?: ITrajet): FormGroup {
        return this.formbuilder.group({
            id: [trip ? trip.id : null],
            busNumber: [trip ? trip.busNumber : '', [Validators.required]],
            dateOfTravel: [trip ? trip.dateOfTravel : '', [Validators.required]],
            price: [trip ? trip.price : null, [Validators.required, Validators.min(0)]]
        });
    }

    addTrip(): void {
        this.trips().push(this.newTrip());
    }

    removeTrip(index: number): void {
        this.trips().removeAt(index);
        this.updateTotalPrice();
    }

    updateTotalPrice(): void {
        const tripsArray = this.trips().value;
        const totalPrice = tripsArray.reduce((acc: number, trip: any) => acc + (trip.price || 0), 0);
        this.form.patchValue({ totalPrice: totalPrice });
    }

    onSubmit() {
        if (this.form.valid) {
            const body = this.form.value;
            body.trips = body.trips.map((trip: any) => {
                const { _price, id, dateOfTravel, ...rest } = trip;
                const momentDate = moment(dateOfTravel, 'DD/MM/YYYY HH:mm:ss');
                const isoString = momentDate.format('YYYY-MM-DDTHH:mm:ss');
                return this.reservation.id ? { id, dateOfTravel: isoString, ...rest } : { dateOfTravel: isoString, ...rest };
            });
            (this.reservation.id ? this.service.update(body) : this.service.create(body)).pipe(
                switchMap((reservation: IReservation) => {
                    let paymentType;
                    (async () => {
                        const paymentModalRef = this.modalService.open(PaymentModalComponent);
                        paymentModalRef.componentInstance.paymentMethods = this.paymentType;
                        paymentType = await paymentModalRef.result;
                    })();
                    return paymentType ? forkJoin([of(reservation), this.service.pay(reservation.id, 'Credit card')]) : [];
                })
            ).subscribe(([reservation, paymentData]: [IReservation, { ReservationId: string, paymentType: string }]) => {
                if (reservation && paymentData) {
                    const reservationData = {
                        date: reservation.trips[0].dateOfTravel,
                        day: moment(reservation.trips[0].dateOfTravel).format('dddd'),
                        time: moment(reservation.trips[0].dateOfTravel).format('HH:mm'),
                        busNumber: reservation.trips[0].busNumber,
                        seats: reservation.trips[0].seatsPerTrip,
                        price: reservation.totalPrice,
                        paymentMethod: paymentData.paymentType
                    };
                    const modalRef = this.modalService.open(ReservationSummaryComponent);
                    modalRef.componentInstance.reservationData = reservationData;
                }
            });
        }
    }

    getAvailableBuses(index: number): Bus[] {
        const selectedBusNumbers = this.trips().controls
            .filter((_, i) => i !== index)
            .map(trip => trip.get('busNumber')?.value)
            .filter(busNumber => busNumber);
        return this.availableBuses.filter(bus => !selectedBusNumbers.includes(bus.busNumber));
    }

    onBusSelection(index: number): void {
        const selectedBusNumber = this.trips().at(index).get('busNumber')?.value;
        const selectedBus = this.availableBuses.find(bus => bus.busNumber === selectedBusNumber);

        if (selectedBus) {
            const dateTime = new Date(selectedBus.departureTime).toLocaleString();
            this.trips().at(index).patchValue({
                dateOfTravel: dateTime,
                price: selectedBus.pricePerTrip
            });

            this.trips().at(index).get('dateOfTravel')?.enable();
            this.trips().at(index).get('price')?.enable();

            this.updateTotalPrice();
        }
    }

    openDeleteConfirmationModal(index: number) {
        this.tripIndexToDelete = index;
        // // Code pour ouvrir la modal Bootstrap
        // const modalElement = document.getElementById('deleteConfirmationModal');
        // if (modalElement) {
        //   const modal = new bootstrap.Modal(modalElement);
        //   modal.show();
        // }
      }
    
    //   removeTrip(index: number) {
    //     // Supprimer le trip à l'index donné
    //     if (this.trips().length > 1) {
    //       this.trips().removeAt(index);
    //     }
    //   }
}