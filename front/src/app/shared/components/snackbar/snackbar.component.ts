import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { SnackbarService } from './snackbar.service';
import { CommonModule } from '@angular/common';
import { MessageType } from './snackbar.model';
import { tap } from 'rxjs';

@Component({
  selector: 'app-snackbar',
  templateUrl: './snackbar.component.html',
  styleUrls: ['./snackbar.component.scss'],
  standalone: true,
  imports: [
    CommonModule
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SnackbarComponent implements OnInit {
  private readonly snackbarService = inject(SnackbarService);
  private readonly destroyRef = inject(DestroyRef);
  show = signal(false);
  message = signal('');
  type = signal<MessageType>(null);
  computedIcon = computed(() => {
    let icon = '';
    switch (this.type()) {
      case 'success': icon = 'icon-checked';
        break;
      case 'error': icon = 'icon-cross';
        break;
      case 'info': icon = 'icon-info';
        break;
    }

    return { icon };
  });

  ngOnInit() {
    this.snackbarService.snackbarState.pipe(
      tap(state => {
        this.type.set(state.type ?? 'success');
        this.message.set(state.message);
        this.show.set(state.show);
        setTimeout(() => {
          this.show.set(false);
        }, this.type() === 'success' ? 3000 : 10000);
      }),
      takeUntilDestroyed(this.destroyRef)
    ).subscribe();
  }
}
