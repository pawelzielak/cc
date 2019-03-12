import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Radio } from 'app/shared/model/radio.model';
import { RadioService } from './radio.service';
import { RadioComponent } from './radio.component';
import { RadioDetailComponent } from './radio-detail.component';
import { RadioUpdateComponent } from './radio-update.component';
import { RadioDeletePopupComponent } from './radio-delete-dialog.component';
import { IRadio } from 'app/shared/model/radio.model';

@Injectable({ providedIn: 'root' })
export class RadioResolve implements Resolve<IRadio> {
    constructor(private service: RadioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRadio> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Radio>) => response.ok),
                map((radio: HttpResponse<Radio>) => radio.body)
            );
        }
        return of(new Radio());
    }
}

export const radioRoute: Routes = [
    {
        path: '',
        component: RadioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.radio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RadioDetailComponent,
        resolve: {
            radio: RadioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.radio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RadioUpdateComponent,
        resolve: {
            radio: RadioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.radio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RadioUpdateComponent,
        resolve: {
            radio: RadioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.radio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const radioPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RadioDeletePopupComponent,
        resolve: {
            radio: RadioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ccApp.radio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
