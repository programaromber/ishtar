import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Evento } from 'app/shared/model/evento.model';
import { EventoService } from './evento.service';
import { EventoComponent } from './evento.component';
import { EventoDetailComponent } from './evento-detail.component';
import { EventoUpdateComponent } from './evento-update.component';
import { EventoDeletePopupComponent } from './evento-delete-dialog.component';
import { IEvento } from 'app/shared/model/evento.model';

@Injectable({ providedIn: 'root' })
export class EventoResolve implements Resolve<IEvento> {
  constructor(private service: EventoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEvento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Evento>) => response.ok),
        map((evento: HttpResponse<Evento>) => evento.body)
      );
    }
    return of(new Evento());
  }
}

export const eventoRoute: Routes = [
  {
    path: '',
    component: EventoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ishtarApp.evento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventoDetailComponent,
    resolve: {
      evento: EventoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.evento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventoUpdateComponent,
    resolve: {
      evento: EventoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.evento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventoUpdateComponent,
    resolve: {
      evento: EventoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.evento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const eventoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EventoDeletePopupComponent,
    resolve: {
      evento: EventoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.evento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
