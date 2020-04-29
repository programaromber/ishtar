import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IParticipante, Participante } from 'app/shared/model/participante.model';
import { ParticipanteService } from './participante.service';
import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from 'app/entities/cidade';

@Component({
  selector: 'jhi-participante-update',
  templateUrl: './participante-update.component.html'
})
export class ParticipanteUpdateComponent implements OnInit {
  isSaving: boolean;

  cidades: ICidade[];
  dataAtualizacaoDp: any;

  editForm = this.fb.group({
    id: [],
    email: [null, [Validators.required, Validators.maxLength(80)]],
    nome: [null, [Validators.maxLength(80)]],
    ddd: [null, [Validators.maxLength(2)]],
    telefone: [null, [Validators.maxLength(10)]],
    notificar: [null, [Validators.required]],
    aceito: [null, [Validators.required]],
    latitude: [],
    longitude: [],
    dataAtualizacao: [null, [Validators.required]],
    cidade: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected participanteService: ParticipanteService,
    protected cidadeService: CidadeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ participante }) => {
      this.updateForm(participante);
    });
    this.cidadeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICidade[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICidade[]>) => response.body)
      )
      .subscribe((res: ICidade[]) => (this.cidades = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(participante: IParticipante) {
    this.editForm.patchValue({
      id: participante.id,
      email: participante.email,
      nome: participante.nome,
      ddd: participante.ddd,
      telefone: participante.telefone,
      notificar: participante.notificar,
      aceito: participante.aceito,
      latitude: participante.latitude,
      longitude: participante.longitude,
      dataAtualizacao: participante.dataAtualizacao,
      cidade: participante.cidade
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const participante = this.createFromForm();
    if (participante.id !== undefined) {
      this.subscribeToSaveResponse(this.participanteService.update(participante));
    } else {
      this.subscribeToSaveResponse(this.participanteService.create(participante));
    }
  }

  private createFromForm(): IParticipante {
    return {
      ...new Participante(),
      id: this.editForm.get(['id']).value,
      email: this.editForm.get(['email']).value,
      nome: this.editForm.get(['nome']).value,
      ddd: this.editForm.get(['ddd']).value,
      telefone: this.editForm.get(['telefone']).value,
      notificar: this.editForm.get(['notificar']).value,
      aceito: this.editForm.get(['aceito']).value,
      latitude: this.editForm.get(['latitude']).value,
      longitude: this.editForm.get(['longitude']).value,
      dataAtualizacao: this.editForm.get(['dataAtualizacao']).value,
      cidade: this.editForm.get(['cidade']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipante>>) {
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

  trackCidadeById(index: number, item: ICidade) {
    return item.id;
  }
}
