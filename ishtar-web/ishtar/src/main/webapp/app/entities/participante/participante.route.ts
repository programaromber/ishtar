import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Participante } from 'app/shared/model/participante.model';
import { ParticipanteService } from './participante.service';
import { ParticipanteComponent } from './participante.component';
import { ParticipanteDetailComponent } from './participante-detail.component';
import { ParticipanteUpdateComponent } from './participante-update.component';
import { ParticipanteDeletePopupComponent } from './participante-delete-dialog.component';
import { IParticipante } from 'app/shared/model/participante.model';

@Injectable({ providedIn: 'root' })
export class ParticipanteResolve implements Resolve<IParticipante> {
  constructor(private service: ParticipanteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IParticipante> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Participante>) => response.ok),
        map((participante: HttpResponse<Participante>) => participante.body)
      );
    }
    return of(new Participante());
  }
}

export const participanteRoute: Routes = [
  {
    path: '',
    component: ParticipanteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ishtarApp.participante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ParticipanteDetailComponent,
    resolve: {
      participante: ParticipanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.participante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ParticipanteUpdateComponent,
    resolve: {
      participante: ParticipanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.participante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ParticipanteUpdateComponent,
    resolve: {
      participante: ParticipanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.participante.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const participantePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ParticipanteDeletePopupComponent,
    resolve: {
      participante: ParticipanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.participante.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
