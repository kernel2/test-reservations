import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ToolBarModule } from '../tool-bar/tool-bar.module';
import { SnackbarModule } from '../snackbar/snackbar.module';
import { SnackbarService } from '../snackbar/snackbar.service';
@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    CommonModule,
    ToolBarModule,
    SnackbarModule,
    RouterOutlet,
  ],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    SnackbarService
  ]
})
export class LayoutComponent {
}
