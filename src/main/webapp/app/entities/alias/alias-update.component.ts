import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAlias } from 'app/shared/model/alias.model';
import { AliasService } from './alias.service';
import { IRadio } from 'app/shared/model/radio.model';
import { RadioService } from 'app/entities/radio';

@Component({
    selector: 'jhi-alias-update',
    templateUrl: './alias-update.component.html'
})
export class AliasUpdateComponent implements OnInit {
    alias: IAlias;
    isSaving: boolean;

    radios: IRadio[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected aliasService: AliasService,
        protected radioService: RadioService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ alias }) => {
            this.alias = alias;
        });
        this.radioService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRadio[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRadio[]>) => response.body)
            )
            .subscribe((res: IRadio[]) => (this.radios = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.alias.id !== undefined) {
            this.subscribeToSaveResponse(this.aliasService.update(this.alias));
        } else {
            this.subscribeToSaveResponse(this.aliasService.create(this.alias));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlias>>) {
        result.subscribe((res: HttpResponse<IAlias>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRadioById(index: number, item: IRadio) {
        return item.id;
    }
}
