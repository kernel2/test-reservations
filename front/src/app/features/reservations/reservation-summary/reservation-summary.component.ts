import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-reservation-summary',
  templateUrl: './reservation-summary.component.html',
  standalone: true,
  imports: [CommonModule],
})
export class ReservationSummaryComponent {
  @Input() reservationData: any;

  constructor(public activeModal: NgbActiveModal) {}

  close() {
    this.activeModal.close();
  }
}
