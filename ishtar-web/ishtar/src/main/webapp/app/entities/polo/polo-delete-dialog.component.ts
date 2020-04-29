import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPolo } from 'app/shared/model/polo.model';
import { PoloService } from './polo.service';

@Component({
  selector: 'jhi-polo-delete-dialog',
  templateUrl: './polo-delete-dialog.component.html'
})
export class PoloDeleteDialogComponent {
  polo: IPolo;

  constructor(protected poloService: PoloService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.poloService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'poloListModification',
        content: 'Deleted an polo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-polo-delete-popup',
  template: ''
})
export class PoloDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ polo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PoloDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.polo = polo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/polo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/polo', { outlets: { popup: null } }]);
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
