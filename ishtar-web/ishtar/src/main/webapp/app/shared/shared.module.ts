import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { IshtarSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [IshtarSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [IshtarSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IshtarSharedModule {
  static forRoot() {
    return {
      ngModule: IshtarSharedModule
    };
  }
}
