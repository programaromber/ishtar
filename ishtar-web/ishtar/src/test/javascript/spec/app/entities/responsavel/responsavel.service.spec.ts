/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ResponsavelService } from 'app/entities/responsavel/responsavel.service';
import { IResponsavel, Responsavel } from 'app/shared/model/responsavel.model';

describe('Service Tests', () => {
  describe('Responsavel Service', () => {
    let injector: TestBed;
    let service: ResponsavelService;
    let httpMock: HttpTestingController;
    let elemDefault: IResponsavel;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ResponsavelService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Responsavel(0, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataCadastro: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Responsavel', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataCadastro: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataCadastro: currentDate
          },
          returnedFromService
        );
        service
          .create(new Responsavel(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Responsavel', async () => {
        const returnedFromService = Object.assign(
          {
            dataCadastro: currentDate.format(DATE_FORMAT),
            ativo: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataCadastro: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Responsavel', async () => {
        const returnedFromService = Object.assign(
          {
            dataCadastro: currentDate.format(DATE_FORMAT),
            ativo: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataCadastro: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Responsavel', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
