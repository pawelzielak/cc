import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAlias } from 'app/shared/model/alias.model';
import { AccountService } from 'app/core';
import { AliasService } from './alias.service';

@Component({
    selector: 'jhi-alias',
    templateUrl: './alias.component.html'
})
export class AliasComponent implements OnInit, OnDestroy {
    aliases: IAlias[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected aliasService: AliasService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.aliasService
            .query()
            .pipe(
                filter((res: HttpResponse<IAlias[]>) => res.ok),
                map((res: HttpResponse<IAlias[]>) => res.body)
            )
            .subscribe(
                (res: IAlias[]) => {
                    this.aliases = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAliases();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAlias) {
        return item.id;
    }

    registerChangeInAliases() {
        this.eventSubscriber = this.eventManager.subscribe('aliasListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
