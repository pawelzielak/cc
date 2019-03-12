import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRadio } from 'app/shared/model/radio.model';
import { RadioService } from './radio.service';

@Component({
    selector: 'jhi-radio-update',
    templateUrl: './radio-update.component.html'
})
export class RadioUpdateComponent implements OnInit {
    radio: IRadio;
    isSaving: boolean;

    constructor(protected radioService: RadioService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ radio }) => {
            this.radio = radio;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.radio.id !== undefined) {
            this.subscribeToSaveResponse(this.radioService.update(this.radio));
        } else {
            this.subscribeToSaveResponse(this.radioService.create(this.radio));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRadio>>) {
        result.subscribe((res: HttpResponse<IRadio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
