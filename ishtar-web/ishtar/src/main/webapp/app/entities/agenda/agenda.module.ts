import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IshtarSharedModule } from 'app/shared';
import {
  AgendaComponent,
  AgendaDetailComponent,
  AgendaUpdateComponent,
  AgendaDeletePopupComponent,
  AgendaDeleteDialogComponent,
  agendaRoute,
  agendaPopupRoute
} from './';

const ENTITY_STATES = [...agendaRoute, ...agendaPopupRoute];

@NgModule({
  imports: [IshtarSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AgendaComponent, AgendaDetailComponent, AgendaUpdateComponent, AgendaDeleteDialogComponent, AgendaDeletePopupComponent],
  entryComponents: [AgendaComponent, AgendaUpdateComponent, AgendaDeleteDialogComponent, AgendaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IshtarAgendaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
