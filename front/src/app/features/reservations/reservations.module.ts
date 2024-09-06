import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReservationsRoutingModule } from './reservations-routing.module';
import { ReservationsComponent } from './reservations.component';


@NgModule({
  imports: [
    CommonModule,
    ReservationsRoutingModule,
    ReservationsComponent,
  ],
  exports: [ReservationsComponent]
})
export class ReservationsModule { }
