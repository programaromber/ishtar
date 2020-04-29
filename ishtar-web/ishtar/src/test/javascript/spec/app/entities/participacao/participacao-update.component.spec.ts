/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IshtarTestModule } from '../../../test.module';
import { ParticipacaoUpdateComponent } from 'app/entities/participacao/participacao-update.component';
import { ParticipacaoService } from 'app/entities/participacao/participacao.service';
import { Participacao } from 'app/shared/model/participacao.model';

describe('Component Tests', () => {
  describe('Participacao Management Update Component', () => {
    let comp: ParticipacaoUpdateComponent;
    let fixture: ComponentFixture<ParticipacaoUpdateComponent>;
    let service: ParticipacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [ParticipacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParticipacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParticipacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParticipacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Participacao(123);
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
        const entity = new Participacao();
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
