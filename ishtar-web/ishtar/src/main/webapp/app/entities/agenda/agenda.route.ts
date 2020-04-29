import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Agenda } from 'app/shared/model/agenda.model';
import { AgendaService } from './agenda.service';
import { AgendaComponent } from './agenda.component';
import { AgendaDetailComponent } from './agenda-detail.component';
import { AgendaUpdateComponent } from './agenda-update.component';
import { AgendaDeletePopupComponent } from './agenda-delete-dialog.component';
import { IAgenda } from 'app/shared/model/agenda.model';

@Injectable({ providedIn: 'root' })
export class AgendaResolve implements Resolve<IAgenda> {
  constructor(private service: AgendaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgenda> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Agenda>) => response.ok),
        map((agenda: HttpResponse<Agenda>) => agenda.body)
      );
    }
    return of(new Agenda());
  }
}

export const agendaRoute: Routes = [
  {
    path: '',
    component: AgendaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ishtarApp.agenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgendaDetailComponent,
    resolve: {
      agenda: AgendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.agenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgendaUpdateComponent,
    resolve: {
      agenda: AgendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.agenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgendaUpdateComponent,
    resolve: {
      agenda: AgendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.agenda.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agendaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgendaDeletePopupComponent,
    resolve: {
      agenda: AgendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.agenda.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
