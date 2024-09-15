import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home.component';
import { ReservationDetailsComponent } from '../reservations/reservation-details/reservation-details.component';
import { ReservationResolver } from '../reservations/reservation.resolver';

const routes: Routes = [
  { 
    path: '', 
    component: HomeComponent 
  },
  { 
    path: 'reservation/:id', 
    component: ReservationDetailsComponent,
    resolve: {
      reservation: ReservationResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
