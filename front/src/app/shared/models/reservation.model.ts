import { Client } from "./client.model";
import { ITrajet } from "./trajet.model";

export interface IReservation {
    id: number;
    clientId: number;
    trips: ITrajet[];
    totalPrice: number;
}

export class Reservation {
    id!: number;
    clientId!: number;
    trips!: ITrajet[];
    totalPrice!: number;

    constructor(apiModel?: IReservation) {
        if (apiModel) {
            this.id = apiModel.id;
            this.clientId = apiModel.clientId;
            this.trips = apiModel.trips;
            this.totalPrice = apiModel.totalPrice;
        }

    }
}