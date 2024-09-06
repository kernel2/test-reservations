import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [
    CommonModule,
  ],
  templateUrl: './reservations.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReservationsComponent {
  protected confirmDelete(): void {
    // please display a NgbModal to confirm which item you try to remove
  }
}
