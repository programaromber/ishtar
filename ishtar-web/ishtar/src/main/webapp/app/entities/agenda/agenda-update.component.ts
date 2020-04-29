import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAgenda, Agenda } from 'app/shared/model/agenda.model';
import { AgendaService } from './agenda.service';
import { IPolo } from 'app/shared/model/polo.model';
import { PoloService } from 'app/entities/polo';

@Component({
  selector: 'jhi-agenda-update',
  templateUrl: './agenda-update.component.html'
})
export class AgendaUpdateComponent implements OnInit {
  isSaving: boolean;

  polos: IPolo[];

  editForm = this.fb.group({
    id: [],
    ano: [null, [Validators.required, Validators.min(4), Validators.max(4)]],
    polo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agendaService: AgendaService,
    protected poloService: PoloService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agenda }) => {
      this.updateForm(agenda);
    });
    this.poloService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPolo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPolo[]>) => response.body)
      )
      .subscribe((res: IPolo[]) => (this.polos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(agenda: IAgenda) {
    this.editForm.patchValue({
      id: agenda.id,
      ano: agenda.ano,
      polo: agenda.polo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agenda = this.createFromForm();
    if (agenda.id !== undefined) {
      this.subscribeToSaveResponse(this.agendaService.update(agenda));
    } else {
      this.subscribeToSaveResponse(this.agendaService.create(agenda));
    }
  }

  private createFromForm(): IAgenda {
    return {
      ...new Agenda(),
      id: this.editForm.get(['id']).value,
      ano: this.editForm.get(['ano']).value,
      polo: this.editForm.get(['polo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgenda>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPoloById(index: number, item: IPolo) {
    return item.id;
  }
}
