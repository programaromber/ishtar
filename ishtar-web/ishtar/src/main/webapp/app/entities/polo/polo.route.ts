import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Polo } from 'app/shared/model/polo.model';
import { PoloService } from './polo.service';
import { PoloComponent } from './polo.component';
import { PoloDetailComponent } from './polo-detail.component';
import { PoloUpdateComponent } from './polo-update.component';
import { PoloDeletePopupComponent } from './polo-delete-dialog.component';
import { IPolo } from 'app/shared/model/polo.model';

@Injectable({ providedIn: 'root' })
export class PoloResolve implements Resolve<IPolo> {
  constructor(private service: PoloService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPolo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Polo>) => response.ok),
        map((polo: HttpResponse<Polo>) => polo.body)
      );
    }
    return of(new Polo());
  }
}

export const poloRoute: Routes = [
  {
    path: '',
    component: PoloComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ishtarApp.polo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PoloDetailComponent,
    resolve: {
      polo: PoloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.polo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PoloUpdateComponent,
    resolve: {
      polo: PoloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.polo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PoloUpdateComponent,
    resolve: {
      polo: PoloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.polo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const poloPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PoloDeletePopupComponent,
    resolve: {
      polo: PoloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ishtarApp.polo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
