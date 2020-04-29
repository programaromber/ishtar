/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IshtarTestModule } from '../../../test.module';
import { ContatoDeleteDialogComponent } from 'app/entities/contato/contato-delete-dialog.component';
import { ContatoService } from 'app/entities/contato/contato.service';

describe('Component Tests', () => {
  describe('Contato Management Delete Component', () => {
    let comp: ContatoDeleteDialogComponent;
    let fixture: ComponentFixture<ContatoDeleteDialogComponent>;
    let service: ContatoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [ContatoDeleteDialogComponent]
      })
        .overrideTemplate(ContatoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContatoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContatoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
