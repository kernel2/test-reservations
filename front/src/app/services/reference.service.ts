// payment-type

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { IReference, Reference } from '../shared/models/reference';

@Injectable({
    providedIn: 'root'
})
export class ReferenceService {

    constructor(private http: HttpClient) {

    }

    getPaymentType(): Observable<Reference[]> {
        return this.http.get<IReference[]>(`/assets/references/payment-type.json`).pipe(
            map((apiModelList: IReference[]) => apiModelList.map((apiModel: IReference) => new Reference(apiModel)))
        );
    }
}