import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Local } from 'app/shared/model/local.model';
import { LocalService } from './local.service';
import { LocalComponent } from './local.component';
import { LocalDetailComponent } from './local-detail.component';
import { LocalUpdateComponent } from './local-update.component';
import { LocalDeletePopupComponent } from './local-delete-dialog.component';
import { ILocal } from 'app/shared/model/local.model';

@Injectable({ providedIn: 'root' })
export class LocalResolve implements Resolve<ILocal> {
  constructor(private service: LocalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILocal> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Local>) => response.ok),
        map((local: HttpResponse<Local>) => local.body)
      );
    }
    return of(new Local());
  }
}

export const localRoute: Routes = [
  {
    path: '',
    component: LocalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ishtarApp.local.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocalDetailComponent,
    resolve: {
      local: LocalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.local.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocalUpdateComponent,
    resolve: {
      local: LocalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.local.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocalUpdateComponent,
    resolve: {
      local: LocalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.local.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const localPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LocalDeletePopupComponent,
    resolve: {
      local: LocalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.local.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
