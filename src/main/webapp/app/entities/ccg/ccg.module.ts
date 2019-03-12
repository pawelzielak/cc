import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CcSharedModule } from 'app/shared';
import {
    CCGComponent,
    CCGDetailComponent,
    CCGUpdateComponent,
    CCGDeletePopupComponent,
    CCGDeleteDialogComponent,
    cCGRoute,
    cCGPopupRoute
} from './';

const ENTITY_STATES = [...cCGRoute, ...cCGPopupRoute];

@NgModule({
    imports: [CcSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CCGComponent, CCGDetailComponent, CCGUpdateComponent, CCGDeleteDialogComponent, CCGDeletePopupComponent],
    entryComponents: [CCGComponent, CCGUpdateComponent, CCGDeleteDialogComponent, CCGDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CcCCGModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
