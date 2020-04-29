/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IshtarTestModule } from '../../../test.module';
import { ParticipanteUpdateComponent } from 'app/entities/participante/participante-update.component';
import { ParticipanteService } from 'app/entities/participante/participante.service';
import { Participante } from 'app/shared/model/participante.model';

describe('Component Tests', () => {
  describe('Participante Management Update Component', () => {
    let comp: ParticipanteUpdateComponent;
    let fixture: ComponentFixture<ParticipanteUpdateComponent>;
    let service: ParticipanteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [ParticipanteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParticipanteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParticipanteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParticipanteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Participante(123);
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
        const entity = new Participante();
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
