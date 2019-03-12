import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CcSharedModule } from 'app/shared';
import {
    FolderComponent,
    FolderDetailComponent,
    FolderUpdateComponent,
    FolderDeletePopupComponent,
    FolderDeleteDialogComponent,
    folderRoute,
    folderPopupRoute
} from './';

const ENTITY_STATES = [...folderRoute, ...folderPopupRoute];

@NgModule({
    imports: [CcSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FolderComponent, FolderDetailComponent, FolderUpdateComponent, FolderDeleteDialogComponent, FolderDeletePopupComponent],
    entryComponents: [FolderComponent, FolderUpdateComponent, FolderDeleteDialogComponent, FolderDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CcFolderModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
