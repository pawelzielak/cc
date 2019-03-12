import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CCG } from 'app/shared/model/ccg.model';
import { CCGService } from './ccg.service';
import { CCGComponent } from './ccg.component';
import { CCGDetailComponent } from './ccg-detail.component';
import { CCGUpdateComponent } from './ccg-update.component';
import { CCGDeletePopupComponent } from './ccg-delete-dialog.component';
import { ICCG } from 'app/shared/model/ccg.model';

@Injectable({ providedIn: 'root' })
export class CCGResolve implements Resolve<ICCG> {
    constructor(private service: CCGService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICCG> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CCG>) => response.ok),
                map((cCG: HttpResponse<CCG>) => cCG.body)
            );
        }
        return of(new CCG());
    }
}

export const cCGRoute: Routes = [
    {
        path: '',
        component: CCGComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.cCG.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CCGDetailComponent,
        resolve: {
            cCG: CCGResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.cCG.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CCGUpdateComponent,
        resolve: {
            cCG: CCGResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.cCG.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CCGUpdateComponent,
        resolve: {
            cCG: CCGResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.cCG.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cCGPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CCGDeletePopupComponent,
        resolve: {
            cCG: CCGResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.cCG.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
