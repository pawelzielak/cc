import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRadio } from 'app/shared/model/radio.model';

@Component({
    selector: 'jhi-radio-detail',
    templateUrl: './radio-detail.component.html'
})
export class RadioDetailComponent implements OnInit {
    radio: IRadio;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ radio }) => {
            this.radio = radio;
        });
    }

    previousState() {
        window.history.back();
    }
}
