/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IshtarTestModule } from '../../../test.module';
import { ContatoUpdateComponent } from 'app/entities/contato/contato-update.component';
import { ContatoService } from 'app/entities/contato/contato.service';
import { Contato } from 'app/shared/model/contato.model';

describe('Component Tests', () => {
  describe('Contato Management Update Component', () => {
    let comp: ContatoUpdateComponent;
    let fixture: ComponentFixture<ContatoUpdateComponent>;
    let service: ContatoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [ContatoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContatoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContatoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContatoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Contato(123);
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
        const entity = new Contato();
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
