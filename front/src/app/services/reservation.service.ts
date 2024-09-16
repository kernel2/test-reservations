import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { IReservation, Reservation } from '../shared/models/reservation.model';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ReservationService {

    constructor(private http: HttpClient) { }

    create(body: IReservation): Observable<IReservation> {
       return this.http.post<IReservation>(`${environment.baseUrl}/reservations`, body).pipe(
            map((apiModel: IReservation) => new Reservation(apiModel))
        );
    }

    getReservations(): Observable<Reservation[]> {
        return this.http.get<IReservation[]>(`${environment.baseUrl}/reservations`).pipe(
            map((apiModelList: IReservation[]) => apiModelList.map((apiModel: IReservation) => new Reservation(apiModel)))
        );
    }

    getReservationByID(id: string): Observable<Reservation> {
        return this.http.get<IReservation>(`${environment.baseUrl}/reservations/${id}`).pipe(
            map((apiModel: IReservation) => new Reservation(apiModel))
        );
    }

    getReservationsByClientID(id: number): Observable<Reservation[]> {
        return this.http.get<IReservation[]>(`${environment.baseUrl}/reservations/client/${id}`).pipe(
            map((apiModelList: IReservation[]) => apiModelList.map((apiModel: IReservation) => new Reservation(apiModel)))
        ); 
    }

    deleteById(id: string): Observable<void> {
        return this.http.delete<void>(`${environment.baseUrl}/reservations/${id}`);
    }

    pay(id: string, type: 'Credit card' | 'PayPal'): Observable<{ ReservationId: string, paymentType: string }> {
        return this.http.post<{ ReservationId: string, paymentType: string }>(`${environment.baseUrl}/reservations/${id}/pay/${type}`, {});
    }

}