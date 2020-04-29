import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAgenda } from 'app/shared/model/agenda.model';

type EntityResponseType = HttpResponse<IAgenda>;
type EntityArrayResponseType = HttpResponse<IAgenda[]>;

@Injectable({ providedIn: 'root' })
export class AgendaService {
  public resourceUrl = SERVER_API_URL + 'api/agenda';

  constructor(protected http: HttpClient) {}

  create(agenda: IAgenda): Observable<EntityResponseType> {
    return this.http.post<IAgenda>(this.resourceUrl, agenda, { observe: 'response' });
  }

  update(agenda: IAgenda): Observable<EntityResponseType> {
    return this.http.put<IAgenda>(this.resourceUrl, agenda, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgenda>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgenda[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
