import { Bus } from "./bus.model";

export interface ITrajet {
    id: number;
    busNumber: string;
    dateOfTravel: Date;
    price: number;
}