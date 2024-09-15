import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { IReservation, Reservation } from '../shared/models/reservation.model';

@Injectable({
    providedIn: 'root'
})
export class ApiService {

    private baseUrl = `http://localhost:8080/api`;


    constructor(private http: HttpClient) {

    }

    getReservations(): Observable<Reservation[]> {
        return this.http.get<IReservation[]>(`${this.baseUrl}/reservations`).pipe(
            map((apiModelList: IReservation[]) => apiModelList.map((apiModel: IReservation) => new Reservation(apiModel)))
        );
    }

    getReservationByID(id: string): Observable<Reservation> {
        return this.http.get<IReservation>(`${this.baseUrl}/reservations/${id}`).pipe(
            map((apiModel: IReservation) => new Reservation(apiModel))
        );
    }



}