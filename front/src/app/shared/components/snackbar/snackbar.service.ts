import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MessageType, Snackbar } from './snackbar.model';

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {
  private readonly snackbarSubject = new BehaviorSubject<Snackbar>({
    show: false,
    message: '',
    type: null
  });
  public   snackbarState = this.snackbarSubject.asObservable();
  private readonly MESSAGE_UPDATE = 'Action effectuée avec succès';
  private readonly MESSAGE_ERROR = 'Une erreur est survenue';

  show(message: string, type: MessageType) {
    this.snackbarSubject.next({
      show: true,
      message,
      type
    });
  }

  showError(message: string = this.MESSAGE_ERROR) : void {
    this.show(message, 'error');
  }

  showSuccess(message: string = this.MESSAGE_UPDATE) : void {
    this.show(message, 'success');
  }
}
