import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICCG } from 'app/shared/model/ccg.model';
import { CCGService } from './ccg.service';

@Component({
    selector: 'jhi-ccg-delete-dialog',
    templateUrl: './ccg-delete-dialog.component.html'
})
export class CCGDeleteDialogComponent {
    cCG: ICCG;

    constructor(protected cCGService: CCGService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cCGService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cCGListModification',
                content: 'Deleted an cCG'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ccg-delete-popup',
    template: ''
})
export class CCGDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cCG }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CCGDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cCG = cCG;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/ccg', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/ccg', { outlets: { popup: null } }]);
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
