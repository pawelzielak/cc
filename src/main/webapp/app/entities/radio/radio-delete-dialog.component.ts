import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRadio } from 'app/shared/model/radio.model';
import { RadioService } from './radio.service';

@Component({
    selector: 'jhi-radio-delete-dialog',
    templateUrl: './radio-delete-dialog.component.html'
})
export class RadioDeleteDialogComponent {
    radio: IRadio;

    constructor(protected radioService: RadioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.radioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'radioListModification',
                content: 'Deleted an radio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-radio-delete-popup',
    template: ''
})
export class RadioDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ radio }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RadioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.radio = radio;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/radio', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/radio', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
