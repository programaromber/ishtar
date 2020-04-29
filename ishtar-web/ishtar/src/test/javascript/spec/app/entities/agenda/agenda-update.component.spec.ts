/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IshtarTestModule } from '../../../test.module';
import { AgendaUpdateComponent } from 'app/entities/agenda/agenda-update.component';
import { AgendaService } from 'app/entities/agenda/agenda.service';
import { Agenda } from 'app/shared/model/agenda.model';

describe('Component Tests', () => {
  describe('Agenda Management Update Component', () => {
    let comp: AgendaUpdateComponent;
    let fixture: ComponentFixture<AgendaUpdateComponent>;
    let service: AgendaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [AgendaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AgendaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgendaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgendaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Agenda(123);
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
        const entity = new Agenda();
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
