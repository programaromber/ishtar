/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IshtarTestModule } from '../../../test.module';
import { PoloUpdateComponent } from 'app/entities/polo/polo-update.component';
import { PoloService } from 'app/entities/polo/polo.service';
import { Polo } from 'app/shared/model/polo.model';

describe('Component Tests', () => {
  describe('Polo Management Update Component', () => {
    let comp: PoloUpdateComponent;
    let fixture: ComponentFixture<PoloUpdateComponent>;
    let service: PoloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [PoloUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PoloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoloUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoloService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Polo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Polo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
