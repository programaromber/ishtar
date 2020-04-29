import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IParticipacao, Participacao } from 'app/shared/model/participacao.model';
import { ParticipacaoService } from './participacao.service';
import { IParticipante } from 'app/shared/model/participante.model';
import { ParticipanteService } from 'app/entities/participante';
import { IEvento } from 'app/shared/model/evento.model';
import { EventoService } from 'app/entities/evento';

@Component({
  selector: 'jhi-participacao-update',
  templateUrl: './participacao-update.component.html'
})
export class ParticipacaoUpdateComponent implements OnInit {
  isSaving: boolean;

  participantes: IParticipante[];

  eventos: IEvento[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(80)]],
    participante: [],
    evento: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected participacaoService: ParticipacaoService,
    protected participanteService: ParticipanteService,
    protected eventoService: EventoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ participacao }) => {
      this.updateForm(participacao);
    });
    this.participanteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IParticipante[]>) => mayBeOk.ok),
        map((response: HttpResponse<IParticipante[]>) => response.body)
      )
      .subscribe((res: IParticipante[]) => (this.participantes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.eventoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEvento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEvento[]>) => response.body)
      )
      .subscribe((res: IEvento[]) => (this.eventos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(participacao: IParticipacao) {
    this.editForm.patchValue({
      id: participacao.id,
      nome: participacao.nome,
      participante: participacao.participante,
      evento: participacao.evento
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const participacao = this.createFromForm();
    if (participacao.id !== undefined) {
      this.subscribeToSaveResponse(this.participacaoService.update(participacao));
    } else {
      this.subscribeToSaveResponse(this.participacaoService.create(participacao));
    }
  }

  private createFromForm(): IParticipacao {
    return {
      ...new Participacao(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      participante: this.editForm.get(['participante']).value,
      evento: this.editForm.get(['evento']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipacao>>) {
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

  trackParticipanteById(index: number, item: IParticipante) {
    return item.id;
  }

  trackEventoById(index: number, item: IEvento) {
    return item.id;
  }
}
