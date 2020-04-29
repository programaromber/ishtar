import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IshtarSharedModule } from 'app/shared';
import {
  ResponsavelComponent,
  ResponsavelDetailComponent,
  ResponsavelUpdateComponent,
  ResponsavelDeletePopupComponent,
  ResponsavelDeleteDialogComponent,
  responsavelRoute,
  responsavelPopupRoute
} from './';

const ENTITY_STATES = [...responsavelRoute, ...responsavelPopupRoute];

@NgModule({
  imports: [IshtarSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ResponsavelComponent,
    ResponsavelDetailComponent,
    ResponsavelUpdateComponent,
    ResponsavelDeleteDialogComponent,
    ResponsavelDeletePopupComponent
  ],
  entryComponents: [ResponsavelComponent, ResponsavelUpdateComponent, ResponsavelDeleteDialogComponent, ResponsavelDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IshtarResponsavelModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
