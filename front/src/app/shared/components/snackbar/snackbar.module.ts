import { NgModule } from '@angular/core';
import { SnackbarComponent } from './snackbar.component'; 
import { SnackbarService } from './snackbar.service';


@NgModule({ 
  providers : [
    SnackbarService,
  ],
  imports: [
    SnackbarComponent
  ],
  exports: [
    SnackbarComponent,
  ]
})
export class SnackbarModule { }
