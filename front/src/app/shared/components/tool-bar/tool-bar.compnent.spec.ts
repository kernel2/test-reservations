import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ToolBarComponent } from './tool-bar.component';
import { ToolBarModule } from './tool-bar.module';

describe('ToolBarComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [RouterTestingModule, ToolBarModule],
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(ToolBarComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
