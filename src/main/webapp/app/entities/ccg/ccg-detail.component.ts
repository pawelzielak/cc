import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICCG } from 'app/shared/model/ccg.model';

@Component({
    selector: 'jhi-ccg-detail',
    templateUrl: './ccg-detail.component.html'
})
export class CCGDetailComponent implements OnInit {
    cCG: ICCG;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cCG }) => {
            this.cCG = cCG;
        });
    }

    previousState() {
        window.history.back();
    }
}
