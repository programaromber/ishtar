/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IshtarTestModule } from '../../../test.module';
import { ParticipanteDetailComponent } from 'app/entities/participante/participante-detail.component';
import { Participante } from 'app/shared/model/participante.model';

describe('Component Tests', () => {
  describe('Participante Management Detail Component', () => {
    let comp: ParticipanteDetailComponent;
    let fixture: ComponentFixture<ParticipanteDetailComponent>;
    const route = ({ data: of({ participante: new Participante(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [ParticipanteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParticipanteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParticipanteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.participante).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
