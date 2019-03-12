import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICCG } from 'app/shared/model/ccg.model';
import { AccountService } from 'app/core';
import { CCGService } from './ccg.service';

@Component({
    selector: 'jhi-ccg',
    templateUrl: './ccg.component.html'
})
export class CCGComponent implements OnInit, OnDestroy {
    cCGS: ICCG[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected cCGService: CCGService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.cCGService
            .query()
            .pipe(
                filter((res: HttpResponse<ICCG[]>) => res.ok),
                map((res: HttpResponse<ICCG[]>) => res.body)
            )
            .subscribe(
                (res: ICCG[]) => {
                    this.cCGS = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCCGS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICCG) {
        return item.id;
    }

    registerChangeInCCGS() {
        this.eventSubscriber = this.eventManager.subscribe('cCGListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
