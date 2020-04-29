import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocal } from 'app/shared/model/local.model';

type EntityResponseType = HttpResponse<ILocal>;
type EntityArrayResponseType = HttpResponse<ILocal[]>;

@Injectable({ providedIn: 'root' })
export class LocalService {
  public resourceUrl = SERVER_API_URL + 'api/locals';

  constructor(protected http: HttpClient) {}

  create(local: ILocal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(local);
    return this.http
      .post<ILocal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(local: ILocal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(local);
    return this.http
      .put<ILocal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILocal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILocal[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(local: ILocal): ILocal {
    const copy: ILocal = Object.assign({}, local, {
      dataCadastro: local.dataCadastro != null && local.dataCadastro.isValid() ? local.dataCadastro.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataCadastro = res.body.dataCadastro != null ? moment(res.body.dataCadastro) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((local: ILocal) => {
        local.dataCadastro = local.dataCadastro != null ? moment(local.dataCadastro) : null;
      });
    }
    return res;
  }
}
