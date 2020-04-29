import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEvento } from 'app/shared/model/evento.model';

@Component({
  selector: 'jhi-evento-detail',
  templateUrl: './evento-detail.component.html'
})
export class EventoDetailComponent implements OnInit {
  evento: IEvento;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ evento }) => {
      this.evento = evento;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
