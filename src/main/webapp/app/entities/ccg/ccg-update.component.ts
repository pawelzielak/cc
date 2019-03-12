import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICCG } from 'app/shared/model/ccg.model';
import { CCGService } from './ccg.service';
import { IRadio } from 'app/shared/model/radio.model';
import { RadioService } from 'app/entities/radio';

@Component({
    selector: 'jhi-ccg-update',
    templateUrl: './ccg-update.component.html'
})
export class CCGUpdateComponent implements OnInit {
    cCG: ICCG;
    isSaving: boolean;

    radios: IRadio[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected cCGService: CCGService,
        protected radioService: RadioService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cCG }) => {
            this.cCG = cCG;
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
        if (this.cCG.id !== undefined) {
            this.subscribeToSaveResponse(this.cCGService.update(this.cCG));
        } else {
            this.subscribeToSaveResponse(this.cCGService.create(this.cCG));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICCG>>) {
        result.subscribe((res: HttpResponse<ICCG>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
