import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IshtarSharedModule } from 'app/shared';
import {
  ParticipacaoComponent,
  ParticipacaoDetailComponent,
  ParticipacaoUpdateComponent,
  ParticipacaoDeletePopupComponent,
  ParticipacaoDeleteDialogComponent,
  participacaoRoute,
  participacaoPopupRoute
} from './';

const ENTITY_STATES = [...participacaoRoute, ...participacaoPopupRoute];

@NgModule({
  imports: [IshtarSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ParticipacaoComponent,
    ParticipacaoDetailComponent,
    ParticipacaoUpdateComponent,
    ParticipacaoDeleteDialogComponent,
    ParticipacaoDeletePopupComponent
  ],
  entryComponents: [
    ParticipacaoComponent,
    ParticipacaoUpdateComponent,
    ParticipacaoDeleteDialogComponent,
    ParticipacaoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IshtarParticipacaoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
