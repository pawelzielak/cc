import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CcSharedModule } from 'app/shared';
import {
    AliasComponent,
    AliasDetailComponent,
    AliasUpdateComponent,
    AliasDeletePopupComponent,
    AliasDeleteDialogComponent,
    aliasRoute,
    aliasPopupRoute
} from './';

const ENTITY_STATES = [...aliasRoute, ...aliasPopupRoute];

@NgModule({
    imports: [CcSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AliasComponent, AliasDetailComponent, AliasUpdateComponent, AliasDeleteDialogComponent, AliasDeletePopupComponent],
    entryComponents: [AliasComponent, AliasUpdateComponent, AliasDeleteDialogComponent, AliasDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CcAliasModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
