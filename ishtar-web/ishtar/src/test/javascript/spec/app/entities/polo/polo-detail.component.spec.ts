/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IshtarTestModule } from '../../../test.module';
import { PoloDetailComponent } from 'app/entities/polo/polo-detail.component';
import { Polo } from 'app/shared/model/polo.model';

describe('Component Tests', () => {
  describe('Polo Management Detail Component', () => {
    let comp: PoloDetailComponent;
    let fixture: ComponentFixture<PoloDetailComponent>;
    const route = ({ data: of({ polo: new Polo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IshtarTestModule],
        declarations: [PoloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PoloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PoloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.polo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
