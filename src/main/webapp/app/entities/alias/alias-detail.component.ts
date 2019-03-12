import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlias } from 'app/shared/model/alias.model';

@Component({
    selector: 'jhi-alias-detail',
    templateUrl: './alias-detail.component.html'
})
export class AliasDetailComponent implements OnInit {
    alias: IAlias;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ alias }) => {
            this.alias = alias;
        });
    }

    previousState() {
        window.history.back();
    }
}
