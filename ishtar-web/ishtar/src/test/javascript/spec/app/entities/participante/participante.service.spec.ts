/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ParticipanteService } from 'app/entities/participante/participante.service';
import { IParticipante, Participante } from 'app/shared/model/participante.model';

describe('Service Tests', () => {
  describe('Participante Service', () => {
    let injector: TestBed;
    let service: ParticipanteService;
    let httpMock: HttpTestingController;
    let elemDefault: IParticipante;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ParticipanteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Participante(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false, false, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataAtualizacao: currentDate.format(DATE_FORMAT)
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

      it('should create a Participante', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataAtualizacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataAtualizacao: currentDate
          },
          returnedFromService
        );
        service
          .create(new Participante(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Participante', async () => {
        const returnedFromService = Object.assign(
          {
            email: 'BBBBBB',
            nome: 'BBBBBB',
            ddd: 'BBBBBB',
            telefone: 'BBBBBB',
            notificar: true,
            aceito: true,
            latitude: 1,
            longitude: 1,
            dataAtualizacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAtualizacao: currentDate
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

      it('should return a list of Participante', async () => {
        const returnedFromService = Object.assign(
          {
            email: 'BBBBBB',
            nome: 'BBBBBB',
            ddd: 'BBBBBB',
            telefone: 'BBBBBB',
            notificar: true,
            aceito: true,
            latitude: 1,
            longitude: 1,
            dataAtualizacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataAtualizacao: currentDate
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

      it('should delete a Participante', async () => {
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
