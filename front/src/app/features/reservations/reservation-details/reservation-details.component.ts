import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';
import { Reservation } from 'src/app/shared/models/reservation.model';

@Component({
    selector: 'app-reservation-details',
    standalone: true,
    imports: [
        CommonModule,
    ],
    templateUrl: './reservation-details.component.html',
    styleUrls: ['./reservation-details.component.scss']
})
export class ReservationDetailsComponent implements OnInit {
    reservationId = '';
    reservation!: Reservation;
    constructor(
        private route: ActivatedRoute,
        private api: ApiService) { }

    ngOnInit(): void {
        this.reservationId = this.route.snapshot.paramMap.get('id') || '';
        if (this.route.snapshot.data && this.route.snapshot.data['reservation']) {
            this.reservation = this.route.snapshot.data['reservation'];
            console.log('reservation', this.reservation);
        }
    }
}