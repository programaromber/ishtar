/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IshtarTestModule } from '../../../test.module';
import { ParticipacaoDetailComponent } from 'app/entities/participacao/participacao-detail.component';
import { Participacao } from 'app/shared/model/participacao.model';

describe('Component Tests', () => {
  describe('Participacao Management Detail Component', () => {
    let comp: ParticipacaoDetailComponent;
    let fixture: ComponentFixture<ParticipacaoDetailComponent>;
    const route = ({ data: of({ participacao: new Participacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [ParticipacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParticipacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParticipacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.participacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
