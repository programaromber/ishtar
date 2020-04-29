/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LocalService } from 'app/entities/local/local.service';
import { ILocal, Local } from 'app/shared/model/local.model';

describe('Service Tests', () => {
  describe('Local Service', () => {
    let injector: TestBed;
    let service: LocalService;
    let httpMock: HttpTestingController;
    let elemDefault: ILocal;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LocalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Local(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, currentDate, false);
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

      it('should create a Local', async () => {
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
          .create(new Local(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Local', async () => {
        const returnedFromService = Object.assign(
          {
            logradouro: 'BBBBBB',
            complememto: 'BBBBBB',
            bairro: 'BBBBBB',
            cep: 'BBBBBB',
            numero: 'BBBBBB',
            latitude: 1,
            longitude: 1,
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

      it('should return a list of Local', async () => {
        const returnedFromService = Object.assign(
          {
            logradouro: 'BBBBBB',
            complememto: 'BBBBBB',
            bairro: 'BBBBBB',
            cep: 'BBBBBB',
            numero: 'BBBBBB',
            latitude: 1,
            longitude: 1,
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

      it('should delete a Local', async () => {
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
