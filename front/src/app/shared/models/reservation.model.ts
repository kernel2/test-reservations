import { Client } from "./client.model";
import { Trajet } from "./trajet.model";

export interface IReservation {
    id: number;
    client : Client;
    trajets: Trajet[];
}

export class Reservation {
    id: number | undefined;
    client : Client | undefined;
    trajets: Trajet[] | undefined;

    constructor(apiModel?: IReservation) {
        if (apiModel) {
            this.id = apiModel.id;
            this.client = apiModel.client;
            this.trajets = apiModel.trajets;
        }

    }
}