import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgenda } from 'app/shared/model/agenda.model';
import { AgendaService } from './agenda.service';

@Component({
  selector: 'jhi-agenda-delete-dialog',
  templateUrl: './agenda-delete-dialog.component.html'
})
export class AgendaDeleteDialogComponent {
  agenda: IAgenda;

  constructor(protected agendaService: AgendaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agendaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agendaListModification',
        content: 'Deleted an agenda'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agenda-delete-popup',
  template: ''
})
export class AgendaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agenda }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgendaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agenda = agenda;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agenda', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agenda', { outlets: { popup: null } }]);
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
