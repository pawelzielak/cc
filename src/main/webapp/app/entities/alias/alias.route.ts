import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Alias } from 'app/shared/model/alias.model';
import { AliasService } from './alias.service';
import { AliasComponent } from './alias.component';
import { AliasDetailComponent } from './alias-detail.component';
import { AliasUpdateComponent } from './alias-update.component';
import { AliasDeletePopupComponent } from './alias-delete-dialog.component';
import { IAlias } from 'app/shared/model/alias.model';

@Injectable({ providedIn: 'root' })
export class AliasResolve implements Resolve<IAlias> {
    constructor(private service: AliasService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAlias> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Alias>) => response.ok),
                map((alias: HttpResponse<Alias>) => alias.body)
            );
        }
        return of(new Alias());
    }
}

export const aliasRoute: Routes = [
    {
        path: '',
        component: AliasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.alias.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AliasDetailComponent,
        resolve: {
            alias: AliasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.alias.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AliasUpdateComponent,
        resolve: {
            alias: AliasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.alias.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AliasUpdateComponent,
        resolve: {
            alias: AliasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.alias.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aliasPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AliasDeletePopupComponent,
        resolve: {
            alias: AliasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.alias.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
