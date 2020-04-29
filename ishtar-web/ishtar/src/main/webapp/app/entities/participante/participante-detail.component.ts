import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParticipante } from 'app/shared/model/participante.model';

@Component({
  selector: 'jhi-participante-detail',
  templateUrl: './participante-detail.component.html'
})
export class ParticipanteDetailComponent implements OnInit {
  participante: IParticipante;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ participante }) => {
      this.participante = participante;
    });
  }

  previousState() {
    window.history.back();
  }
}
