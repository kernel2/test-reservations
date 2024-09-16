import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ReservationService } from "../../services/reservation.service";
import { Reservation } from "../../shared/models/reservation.model";
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-reservations',
    standalone: true,
    imports: [
        CommonModule,
        RouterModule
    ],
    templateUrl: './reservations.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})

export class ReservationsComponent implements OnInit {

    reservationList!: Reservation[];

    constructor(
        private reservationService: ReservationService,
        private changeDetectorRef: ChangeDetectorRef) {
    }

    protected confirmDelete(): void {
        // please display a NgbModal to confirm which item you try to remove
    }

    ngOnInit(): void {
        this.reservationService.getReservationsByClientID(1).subscribe({
            next: (list: Reservation[]) => {
                this.reservationList = list;
                this.changeDetectorRef.detectChanges();
                console.log("error reservation list :", this.reservationList);
            }
        });
    }
}
