import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgenda } from 'app/shared/model/agenda.model';

@Component({
  selector: 'jhi-agenda-detail',
  templateUrl: './agenda-detail.component.html'
})
export class AgendaDetailComponent implements OnInit {
  agenda: IAgenda;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agenda }) => {
      this.agenda = agenda;
    });
  }

  previousState() {
    window.history.back();
  }
}
