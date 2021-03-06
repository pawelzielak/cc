import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'folder',
                loadChildren: './folder/folder.module#CcFolderModule'
            },
            {
                path: 'alias',
                loadChildren: './alias/alias.module#CcAliasModule'
            },
            {
                path: 'radio',
                loadChildren: './radio/radio.module#CcRadioModule'
            },
            {
                path: 'ccg',
                loadChildren: './ccg/ccg.module#CcCCGModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CcEntityModule {}
