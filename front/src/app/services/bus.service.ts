import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Bus, IBus } from '../shared/models/bus.model';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class BusService {

    constructor(private http: HttpClient) {

    }


    list(): Observable<Bus[]> {
        return this.http.get<IBus[]>(`${environment.baseUrl}/buses`).pipe(
            map((apiModelList: IBus[]) => apiModelList.map((apiModel: IBus) => new Bus(apiModel)))
        );
    }
}