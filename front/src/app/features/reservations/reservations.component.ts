import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ReservationService } from "../../services/reservation.service";
import { Reservation } from "../../shared/models/reservation.model";
import { Router, RouterModule } from '@angular/router';

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
        private router: Router,
        private changeDetectorRef: ChangeDetectorRef) {
    }


    navigateToEdit(reservationId: number) {
        this.router.navigate(['/reservations', reservationId]);
    }

    protected confirmDelete(reservationId: number) {
        // Logique pour afficher la modale de confirmation de suppression
        const modalElement = document.getElementById('deleteConfirmationModal');
        if (modalElement) {
            // const modal = new bootstrap.Modal(modalElement);
            // modal.show();
        }
    }

    ngOnInit(): void {
        this.reservationService.getReservationsByClientID(1).subscribe({
            next: (list: Reservation[]) => {
                this.reservationList = list;
                this.changeDetectorRef.detectChanges();
            }
        });
    }
}
