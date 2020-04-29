import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Participacao } from 'app/shared/model/participacao.model';
import { ParticipacaoService } from './participacao.service';
import { ParticipacaoComponent } from './participacao.component';
import { ParticipacaoDetailComponent } from './participacao-detail.component';
import { ParticipacaoUpdateComponent } from './participacao-update.component';
import { ParticipacaoDeletePopupComponent } from './participacao-delete-dialog.component';
import { IParticipacao } from 'app/shared/model/participacao.model';

@Injectable({ providedIn: 'root' })
export class ParticipacaoResolve implements Resolve<IParticipacao> {
  constructor(private service: ParticipacaoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IParticipacao> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Participacao>) => response.ok),
        map((participacao: HttpResponse<Participacao>) => participacao.body)
      );
    }
    return of(new Participacao());
  }
}

export const participacaoRoute: Routes = [
  {
    path: '',
    component: ParticipacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ishtarApp.participacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ParticipacaoDetailComponent,
    resolve: {
      participacao: ParticipacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.participacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ParticipacaoUpdateComponent,
    resolve: {
      participacao: ParticipacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.participacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ParticipacaoUpdateComponent,
    resolve: {
      participacao: ParticipacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.participacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const participacaoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ParticipacaoDeletePopupComponent,
    resolve: {
      participacao: ParticipacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.participacao.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
