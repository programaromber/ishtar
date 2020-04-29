/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IshtarTestModule } from '../../../test.module';
import { ResponsavelDeleteDialogComponent } from 'app/entities/responsavel/responsavel-delete-dialog.component';
import { ResponsavelService } from 'app/entities/responsavel/responsavel.service';

describe('Component Tests', () => {
  describe('Responsavel Management Delete Component', () => {
    let comp: ResponsavelDeleteDialogComponent;
    let fixture: ComponentFixture<ResponsavelDeleteDialogComponent>;
    let service: ResponsavelService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [ResponsavelDeleteDialogComponent]
      })
        .overrideTemplate(ResponsavelDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResponsavelDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResponsavelService);
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
