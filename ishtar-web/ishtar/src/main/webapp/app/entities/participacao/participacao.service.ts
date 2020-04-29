import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParticipacao } from 'app/shared/model/participacao.model';

type EntityResponseType = HttpResponse<IParticipacao>;
type EntityArrayResponseType = HttpResponse<IParticipacao[]>;

@Injectable({ providedIn: 'root' })
export class ParticipacaoService {
  public resourceUrl = SERVER_API_URL + 'api/participacaos';

  constructor(protected http: HttpClient) {}

  create(participacao: IParticipacao): Observable<EntityResponseType> {
    return this.http.post<IParticipacao>(this.resourceUrl, participacao, { observe: 'response' });
  }

  update(participacao: IParticipacao): Observable<EntityResponseType> {
    return this.http.put<IParticipacao>(this.resourceUrl, participacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParticipacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParticipacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
