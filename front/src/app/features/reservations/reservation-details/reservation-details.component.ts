import {CommonModule} from '@angular/common';
import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {BusService} from 'src/app/services/bus.service';
import {ReservationService} from 'src/app/services/reservation.service';
import {Bus} from 'src/app/shared/models/bus.model';
import {Reservation} from 'src/app/shared/models/reservation.model';

import * as moment from 'moment';

@Component({
    selector: 'app-reservation-details',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        FormsModule,
        CommonModule,

    ],
    templateUrl: './reservation-details.component.html',
    styleUrls: ['./reservation-details.component.scss']
})
export class ReservationDetailsComponent implements OnInit {
    reservationId = '';
    reservation!: Reservation;
    form!: FormGroup;

    availableBuses: Bus[] = [];

    constructor(
        private route: ActivatedRoute,
        private formbuilder: FormBuilder,
        private busService: BusService,
        private router: Router,
        private service: ReservationService) {
        this.form = this.formbuilder.group({
            clientId: [1, [Validators.required]],
            trips: this.formbuilder.array([]),
            totalPrice: [null, [Validators.required]]
        });
    }

    ngOnInit(): void {
        this.reservationId = this.route.snapshot.paramMap.get('id') || '';
        if (this.route.snapshot.data && this.route.snapshot.data['reservation']) {
            this.reservation = this.route.snapshot.data['reservation'];
            this.form.patchValue(this.reservation);
        }

        this.busService.list().subscribe({
            next: (list: Bus[]) => {
                this.availableBuses = list;
            }
        });
    }

    trips(): FormArray {
        return this.form.get('trips') as FormArray;
    }

    newTrip(): FormGroup {
        return this.formbuilder.group({
            id: [null],
            busNumber: ['', [Validators.required]],
            dateOfTravel: ['', [Validators.required]],
            seatsPerTrip: ['', [Validators.required]],
            price: [null, [Validators.required, Validators.min(0)]]
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
        this.form.patchValue({totalPrice: totalPrice});
    }

    onSubmit() {
        if (this.form.valid) {
            const body = this.form.value;
            body.trips = body.trips.map((trip: any) => {
                const {_price, id, dateOfTravel, seatsPerTrip, ...rest} = trip;
                const momentDate = moment(dateOfTravel, 'DD/MM/YYYY HH:mm:ss');
                const isoString = momentDate.format('YYYY-MM-DDTHH:mm:ss');
                return {dateOfTravel: isoString, ...rest};
            });
            this.service.create(body).subscribe(() => {
                this.router.navigate(['reservations']);
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
            this.trips().at(index).get('seatsPerTrip')?.enable();
            this.trips().at(index).get('price')?.enable();

            this.updateTotalPrice();
        }
    }

}