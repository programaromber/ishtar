import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContato } from 'app/shared/model/contato.model';

@Component({
  selector: 'jhi-contato-detail',
  templateUrl: './contato-detail.component.html'
})
export class ContatoDetailComponent implements OnInit {
  contato: IContato;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contato }) => {
      this.contato = contato;
    });
  }

  previousState() {
    window.history.back();
  }
}
