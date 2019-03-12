import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlias } from 'app/shared/model/alias.model';
import { AliasService } from './alias.service';

@Component({
    selector: 'jhi-alias-delete-dialog',
    templateUrl: './alias-delete-dialog.component.html'
})
export class AliasDeleteDialogComponent {
    alias: IAlias;

    constructor(protected aliasService: AliasService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aliasService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'aliasListModification',
                content: 'Deleted an alias'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-alias-delete-popup',
    template: ''
})
export class AliasDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ alias }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AliasDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.alias = alias;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/alias', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/alias', { outlets: { popup: null } }]);
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
