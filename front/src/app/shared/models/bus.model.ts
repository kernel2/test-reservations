export interface IBus {
    busNumber: string;
    seatsPerTrip: number;
    departureTime: Date;
    pricePerTrip: number;
}

export class Bus {
    busNumber!: string;
    seatsPerTrip!: number;
    departureTime!: Date;
    pricePerTrip!: number;

    constructor(apiModel?: IBus) {
        if (apiModel) {
            this.busNumber = apiModel.busNumber;
            this.seatsPerTrip = apiModel.seatsPerTrip;
            this.departureTime = apiModel.departureTime;
            this.pricePerTrip = apiModel.pricePerTrip;
        }
    }
}