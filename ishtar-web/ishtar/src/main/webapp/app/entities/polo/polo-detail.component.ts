import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPolo } from 'app/shared/model/polo.model';

@Component({
  selector: 'jhi-polo-detail',
  templateUrl: './polo-detail.component.html'
})
export class PoloDetailComponent implements OnInit {
  polo: IPolo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ polo }) => {
      this.polo = polo;
    });
  }

  previousState() {
    window.history.back();
  }
}
