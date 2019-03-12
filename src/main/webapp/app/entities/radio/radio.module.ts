import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CcSharedModule } from 'app/shared';
import {
    RadioComponent,
    RadioDetailComponent,
    RadioUpdateComponent,
    RadioDeletePopupComponent,
    RadioDeleteDialogComponent,
    radioRoute,
    radioPopupRoute
} from './';

const ENTITY_STATES = [...radioRoute, ...radioPopupRoute];

@NgModule({
    imports: [CcSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RadioComponent, RadioDetailComponent, RadioUpdateComponent, RadioDeleteDialogComponent, RadioDeletePopupComponent],
    entryComponents: [RadioComponent, RadioUpdateComponent, RadioDeleteDialogComponent, RadioDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CcRadioModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
