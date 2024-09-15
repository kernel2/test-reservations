import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn, Router } from '@angular/router';
import { catchError, filter, Observable, of, take } from 'rxjs';
import { ApiService } from 'src/app/services/api.service';
import { Reservation } from 'src/app/shared/models/reservation.model';

export const ReservationResolver: ResolveFn<Reservation | null> = (
    route: ActivatedRouteSnapshot
): Observable<Reservation | null> => {

    const apiService = inject(ApiService);
    const router = inject(Router);
    const id = route.paramMap.get('id');
    if (id) {
        return apiService.getReservationByID(id).pipe(
            catchError((_err, _caught) => {
                router.navigate(['/home']);
                return of(null);
            })
        );
    }
    return of(null);
};

