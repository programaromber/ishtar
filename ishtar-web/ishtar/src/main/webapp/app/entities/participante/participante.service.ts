import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParticipante } from 'app/shared/model/participante.model';

type EntityResponseType = HttpResponse<IParticipante>;
type EntityArrayResponseType = HttpResponse<IParticipante[]>;

@Injectable({ providedIn: 'root' })
export class ParticipanteService {
  public resourceUrl = SERVER_API_URL + 'api/participantes';

  constructor(protected http: HttpClient) {}

  create(participante: IParticipante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(participante);
    return this.http
      .post<IParticipante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(participante: IParticipante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(participante);
    return this.http
      .put<IParticipante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParticipante>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParticipante[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(participante: IParticipante): IParticipante {
    const copy: IParticipante = Object.assign({}, participante, {
      dataAtualizacao:
        participante.dataAtualizacao != null && participante.dataAtualizacao.isValid()
          ? participante.dataAtualizacao.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataAtualizacao = res.body.dataAtualizacao != null ? moment(res.body.dataAtualizacao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((participante: IParticipante) => {
        participante.dataAtualizacao = participante.dataAtualizacao != null ? moment(participante.dataAtualizacao) : null;
      });
    }
    return res;
  }
}
