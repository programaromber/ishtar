import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IshtarSharedModule } from 'app/shared';
import {
  PoloComponent,
  PoloDetailComponent,
  PoloUpdateComponent,
  PoloDeletePopupComponent,
  PoloDeleteDialogComponent,
  poloRoute,
  poloPopupRoute
} from './';

const ENTITY_STATES = [...poloRoute, ...poloPopupRoute];

@NgModule({
  imports: [IshtarSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PoloComponent, PoloDetailComponent, PoloUpdateComponent, PoloDeleteDialogComponent, PoloDeletePopupComponent],
  entryComponents: [PoloComponent, PoloUpdateComponent, PoloDeleteDialogComponent, PoloDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IshtarPoloModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
