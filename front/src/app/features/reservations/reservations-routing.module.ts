import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReservationsComponent } from './reservations.component';
import { ReservationDetailsComponent } from './reservation-details/reservation-details.component';
import { ReservationResolver } from './reservation.resolver';
import { BusResolver } from './bus.resolver';

const routes: Routes = [
  { 
    path: '',
    component: ReservationsComponent 
  },
  { 
    path: 'cree', 
    component: ReservationDetailsComponent,
    resolve: {
      busList: BusResolver
    }
  },
  { 
    path: ':id', 
    component: ReservationDetailsComponent,
    resolve: {
      reservation: ReservationResolver,
      busList: BusResolver
    }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReservationsRoutingModule { }
