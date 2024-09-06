import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-tool-bar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: 'tool-bar.component.html',
  styleUrls: ['tool-bar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ToolBarComponent { }
