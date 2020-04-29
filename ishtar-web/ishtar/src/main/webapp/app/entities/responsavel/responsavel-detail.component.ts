import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResponsavel } from 'app/shared/model/responsavel.model';

@Component({
  selector: 'jhi-responsavel-detail',
  templateUrl: './responsavel-detail.component.html'
})
export class ResponsavelDetailComponent implements OnInit {
  responsavel: IResponsavel;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ responsavel }) => {
      this.responsavel = responsavel;
    });
  }

  previousState() {
    window.history.back();
  }
}
