import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn, Router } from '@angular/router';
import { catchError, Observable, of } from 'rxjs';
import { ReservationService } from 'src/app/services/reservation.service';
import { Reservation } from 'src/app/shared/models/reservation.model';

export const ReservationResolver: ResolveFn<Reservation | null> = (
    route: ActivatedRouteSnapshot
): Observable<Reservation | null> => {

    const service = inject(ReservationService);
    const router = inject(Router);
    const id = route.paramMap.get('id');
    if (id) {
        return service.getReservationByID(id).pipe(
            catchError((_err, _caught) => {
                router.navigate(['/home']);
                return of(null);
            })
        );
    }
    return of(null);
};

