import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRadio } from 'app/shared/model/radio.model';
import { AccountService } from 'app/core';
import { RadioService } from './radio.service';

@Component({
    selector: 'jhi-radio',
    templateUrl: './radio.component.html'
})
export class RadioComponent implements OnInit, OnDestroy {
    radios: IRadio[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected radioService: RadioService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.radioService
            .query()
            .pipe(
                filter((res: HttpResponse<IRadio[]>) => res.ok),
                map((res: HttpResponse<IRadio[]>) => res.body)
            )
            .subscribe(
                (res: IRadio[]) => {
                    this.radios = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRadios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRadio) {
        return item.id;
    }

    registerChangeInRadios() {
        this.eventSubscriber = this.eventManager.subscribe('radioListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
