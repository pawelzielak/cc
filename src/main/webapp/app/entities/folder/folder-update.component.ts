import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFolder } from 'app/shared/model/folder.model';
import { FolderService } from './folder.service';
import { IAlias } from 'app/shared/model/alias.model';
import { AliasService } from 'app/entities/alias';
import { IRadio } from 'app/shared/model/radio.model';
import { RadioService } from 'app/entities/radio';

@Component({
    selector: 'jhi-folder-update',
    templateUrl: './folder-update.component.html'
})
export class FolderUpdateComponent implements OnInit {
    folder: IFolder;
    isSaving: boolean;

    aliases: IAlias[];

    radios: IRadio[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected folderService: FolderService,
        protected aliasService: AliasService,
        protected radioService: RadioService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ folder }) => {
            this.folder = folder;
        });
        this.aliasService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAlias[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAlias[]>) => response.body)
            )
            .subscribe((res: IAlias[]) => (this.aliases = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.folder.id !== undefined) {
            this.subscribeToSaveResponse(this.folderService.update(this.folder));
        } else {
            this.subscribeToSaveResponse(this.folderService.create(this.folder));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFolder>>) {
        result.subscribe((res: HttpResponse<IFolder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAliasById(index: number, item: IAlias) {
        return item.id;
    }

    trackRadioById(index: number, item: IRadio) {
        return item.id;
    }
}
