import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParticipacao } from 'app/shared/model/participacao.model';
import { ParticipacaoService } from './participacao.service';

@Component({
  selector: 'jhi-participacao-delete-dialog',
  templateUrl: './participacao-delete-dialog.component.html'
})
export class ParticipacaoDeleteDialogComponent {
  participacao: IParticipacao;

  constructor(
    protected participacaoService: ParticipacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.participacaoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'participacaoListModification',
        content: 'Deleted an participacao'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-participacao-delete-popup',
  template: ''
})
export class ParticipacaoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ participacao }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ParticipacaoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.participacao = participacao;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/participacao', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/participacao', { outlets: { popup: null } }]);
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
