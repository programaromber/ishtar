import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContato } from 'app/shared/model/contato.model';

type EntityResponseType = HttpResponse<IContato>;
type EntityArrayResponseType = HttpResponse<IContato[]>;

@Injectable({ providedIn: 'root' })
export class ContatoService {
  public resourceUrl = SERVER_API_URL + 'api/contatoes';

  constructor(protected http: HttpClient) {}

  create(contato: IContato): Observable<EntityResponseType> {
    return this.http.post<IContato>(this.resourceUrl, contato, { observe: 'response' });
  }

  update(contato: IContato): Observable<EntityResponseType> {
    return this.http.put<IContato>(this.resourceUrl, contato, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContato>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContato[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
