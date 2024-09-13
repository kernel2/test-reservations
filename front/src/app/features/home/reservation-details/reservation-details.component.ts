import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-reservation-details',
    templateUrl: './reservation-details.component.html',
    styleUrls: ['./reservation-details.component.css']
})
export class ReservationDetailsComponent implements OnInit {
    reservationId = '';

    constructor(private route: ActivatedRoute) { }

    ngOnInit(): void {
        // Récupérer l'ID de la réservation à partir de l'URL
        this.reservationId = this.route.snapshot.paramMap.get('id') || '';

        // Tu peux maintenant utiliser cet ID pour charger les détails de la réservation
    }
}