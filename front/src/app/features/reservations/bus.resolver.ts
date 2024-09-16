import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn, Router } from '@angular/router';
import { catchError, Observable, of } from 'rxjs';
import { BusService } from 'src/app/services/bus.service';
import { Bus } from 'src/app/shared/models/bus.model';

export const BusResolver: ResolveFn<Bus[] | null> = (
    route: ActivatedRouteSnapshot
): Observable<Bus[] | null> => {

    const service = inject(BusService);
    const router = inject(Router);
    return service.list().pipe(
        catchError((_err, _caught) => {
            router.navigate(['/home']);
            return of(null);
        })
    );
};

