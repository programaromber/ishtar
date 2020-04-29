/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IshtarTestModule } from '../../../test.module';
import { ResponsavelUpdateComponent } from 'app/entities/responsavel/responsavel-update.component';
import { ResponsavelService } from 'app/entities/responsavel/responsavel.service';
import { Responsavel } from 'app/shared/model/responsavel.model';

describe('Component Tests', () => {
  describe('Responsavel Management Update Component', () => {
    let comp: ResponsavelUpdateComponent;
    let fixture: ComponentFixture<ResponsavelUpdateComponent>;
    let service: ResponsavelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [ResponsavelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResponsavelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResponsavelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResponsavelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Responsavel(123);
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
        const entity = new Responsavel();
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
