import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IshtarSharedModule } from 'app/shared';
import {
  ContatoComponent,
  ContatoDetailComponent,
  ContatoUpdateComponent,
  ContatoDeletePopupComponent,
  ContatoDeleteDialogComponent,
  contatoRoute,
  contatoPopupRoute
} from './';

const ENTITY_STATES = [...contatoRoute, ...contatoPopupRoute];

@NgModule({
  imports: [IshtarSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContatoComponent,
    ContatoDetailComponent,
    ContatoUpdateComponent,
    ContatoDeleteDialogComponent,
    ContatoDeletePopupComponent
  ],
  entryComponents: [ContatoComponent, ContatoUpdateComponent, ContatoDeleteDialogComponent, ContatoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IshtarContatoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
