import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPolo } from 'app/shared/model/polo.model';

type EntityResponseType = HttpResponse<IPolo>;
type EntityArrayResponseType = HttpResponse<IPolo[]>;

@Injectable({ providedIn: 'root' })
export class PoloService {
  public resourceUrl = SERVER_API_URL + 'api/polos';

  constructor(protected http: HttpClient) {}

  create(polo: IPolo): Observable<EntityResponseType> {
    return this.http.post<IPolo>(this.resourceUrl, polo, { observe: 'response' });
  }

  update(polo: IPolo): Observable<EntityResponseType> {
    return this.http.put<IPolo>(this.resourceUrl, polo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPolo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPolo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
