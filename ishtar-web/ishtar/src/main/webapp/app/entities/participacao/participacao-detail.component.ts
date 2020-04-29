import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParticipacao } from 'app/shared/model/participacao.model';

@Component({
  selector: 'jhi-participacao-detail',
  templateUrl: './participacao-detail.component.html'
})
export class ParticipacaoDetailComponent implements OnInit {
  participacao: IParticipacao;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ participacao }) => {
      this.participacao = participacao;
    });
  }

  previousState() {
    window.history.back();
  }
}
