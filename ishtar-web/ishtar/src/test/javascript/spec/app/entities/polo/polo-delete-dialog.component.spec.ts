/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IshtarTestModule } from '../../../test.module';
import { PoloDeleteDialogComponent } from 'app/entities/polo/polo-delete-dialog.component';
import { PoloService } from 'app/entities/polo/polo.service';

describe('Component Tests', () => {
  describe('Polo Management Delete Component', () => {
    let comp: PoloDeleteDialogComponent;
    let fixture: ComponentFixture<PoloDeleteDialogComponent>;
    let service: PoloService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [PoloDeleteDialogComponent]
      })
        .overrideTemplate(PoloDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PoloDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoloService);
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
