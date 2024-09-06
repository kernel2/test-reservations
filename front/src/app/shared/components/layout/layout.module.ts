import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutRoutingModule, } from './layout-routing.module';
import { LayoutComponent } from './layout.component';
import { SnackbarModule } from '../snackbar/snackbar.module';

@NgModule({ 

  imports: [
    CommonModule,
    LayoutRoutingModule,
    LayoutComponent,
    SnackbarModule,
  ],
  exports: [
    LayoutComponent
  ]
})
export class LayoutModule { }
