import { TestBed } from '@angular/core/testing';
import { SnackbarService } from './snackbar.service';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { I18nHttpLoaderFactory } from '../../utils/factories';
import { HttpClient } from '@angular/common/http';

describe('SnackbarService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: I18nHttpLoaderFactory,
        deps: [HttpClient]
      }
    })]
  }));

  it('should be created', () => {
    const service: SnackbarService = TestBed.inject(SnackbarService);
    expect(service).toBeTruthy();
  });
});
