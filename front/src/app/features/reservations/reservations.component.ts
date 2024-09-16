import {CommonModule} from '@angular/common';
import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {ReservationService} from "../../services/reservation.service";
import {Reservation} from "../../shared/models/reservation.model";

@Component({
    selector: 'app-reservations',
    standalone: true,
    imports: [
        CommonModule,
    ],
    templateUrl: './reservations.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})

export class ReservationsComponent implements OnInit {

    reservationList: Reservation[] = [];

    constructor(private reservationService: ReservationService) {
    }

    protected confirmDelete(): void {
        // please display a NgbModal to confirm which item you try to remove
    }

    ngOnInit(): void {
        this.reservationService.getReservationsByClientID(1).subscribe({

            next: (list: Reservation[]) => {
                this.reservationList = list;
            }, error(err: any): void {
                console.log("error reservation list :", err);
            }
        });
    }
}
