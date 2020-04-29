import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IshtarSharedModule } from 'app/shared';
import {
  ParticipanteComponent,
  ParticipanteDetailComponent,
  ParticipanteUpdateComponent,
  ParticipanteDeletePopupComponent,
  ParticipanteDeleteDialogComponent,
  participanteRoute,
  participantePopupRoute
} from './';

const ENTITY_STATES = [...participanteRoute, ...participantePopupRoute];

@NgModule({
  imports: [IshtarSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ParticipanteComponent,
    ParticipanteDetailComponent,
    ParticipanteUpdateComponent,
    ParticipanteDeleteDialogComponent,
    ParticipanteDeletePopupComponent
  ],
  entryComponents: [
    ParticipanteComponent,
    ParticipanteUpdateComponent,
    ParticipanteDeleteDialogComponent,
    ParticipanteDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IshtarParticipanteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
