import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IshtarSharedModule } from 'app/shared';
import {
  LocalComponent,
  LocalDetailComponent,
  LocalUpdateComponent,
  LocalDeletePopupComponent,
  LocalDeleteDialogComponent,
  localRoute,
  localPopupRoute
} from './';

const ENTITY_STATES = [...localRoute, ...localPopupRoute];

@NgModule({
  imports: [IshtarSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [LocalComponent, LocalDetailComponent, LocalUpdateComponent, LocalDeleteDialogComponent, LocalDeletePopupComponent],
  entryComponents: [LocalComponent, LocalUpdateComponent, LocalDeleteDialogComponent, LocalDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IshtarLocalModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
