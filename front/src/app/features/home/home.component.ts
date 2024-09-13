import { Component, OnInit } from '@angular/core';


interface Reservation {
  id: string;
  clientId: string;
  busNumber: string;
  dateOfTravel: string;
  price: number;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  reservations: Reservation[] = [];

  ngOnInit(): void {
    this.reservations = [
      { id: '5464984', clientId: 'mklhkgjfhd654684365465', busNumber: 'BUS123', dateOfTravel: '2024-09-15T08:30:00', price: 50.00 },
      { id: '5464986', clientId: 'mklhkgjfhd654684365465', busNumber: 'BUS456', dateOfTravel: '2024-09-20T10:00:00', price: 75.00 },
      { id: '5464987', clientId: 'mklhkgjfhd654684365465', busNumber: 'BUS789', dateOfTravel: '2024-10-01T14:45:00', price: 60.00 }
    ];
  }


}
