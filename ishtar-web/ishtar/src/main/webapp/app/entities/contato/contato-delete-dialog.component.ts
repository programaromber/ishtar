import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContato } from 'app/shared/model/contato.model';
import { ContatoService } from './contato.service';

@Component({
  selector: 'jhi-contato-delete-dialog',
  templateUrl: './contato-delete-dialog.component.html'
})
export class ContatoDeleteDialogComponent {
  contato: IContato;

  constructor(protected contatoService: ContatoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contatoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contatoListModification',
        content: 'Deleted an contato'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-contato-delete-popup',
  template: ''
})
export class ContatoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contato }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContatoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.contato = contato;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/contato', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/contato', { outlets: { popup: null } }]);
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
