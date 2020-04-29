import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILocal, Local } from 'app/shared/model/local.model';
import { LocalService } from './local.service';
import { IPolo } from 'app/shared/model/polo.model';
import { PoloService } from 'app/entities/polo';

@Component({
  selector: 'jhi-local-update',
  templateUrl: './local-update.component.html'
})
export class LocalUpdateComponent implements OnInit {
  isSaving: boolean;

  polos: IPolo[];
  dataCadastroDp: any;

  editForm = this.fb.group({
    id: [],
    logradouro: [null, [Validators.required, Validators.maxLength(80)]],
    complememto: [null, [Validators.maxLength(80)]],
    bairro: [null, [Validators.required, Validators.maxLength(80)]],
    cep: [null, [Validators.required, Validators.maxLength(8)]],
    numero: [null, [Validators.required, Validators.maxLength(8)]],
    latitude: [],
    longitude: [],
    dataCadastro: [null, [Validators.required]],
    ativo: [null, [Validators.required]],
    polo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected localService: LocalService,
    protected poloService: PoloService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ local }) => {
      this.updateForm(local);
    });
    this.poloService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPolo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPolo[]>) => response.body)
      )
      .subscribe((res: IPolo[]) => (this.polos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(local: ILocal) {
    this.editForm.patchValue({
      id: local.id,
      logradouro: local.logradouro,
      complememto: local.complememto,
      bairro: local.bairro,
      cep: local.cep,
      numero: local.numero,
      latitude: local.latitude,
      longitude: local.longitude,
      dataCadastro: local.dataCadastro,
      ativo: local.ativo,
      polo: local.polo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const local = this.createFromForm();
    if (local.id !== undefined) {
      this.subscribeToSaveResponse(this.localService.update(local));
    } else {
      this.subscribeToSaveResponse(this.localService.create(local));
    }
  }

  private createFromForm(): ILocal {
    return {
      ...new Local(),
      id: this.editForm.get(['id']).value,
      logradouro: this.editForm.get(['logradouro']).value,
      complememto: this.editForm.get(['complememto']).value,
      bairro: this.editForm.get(['bairro']).value,
      cep: this.editForm.get(['cep']).value,
      numero: this.editForm.get(['numero']).value,
      latitude: this.editForm.get(['latitude']).value,
      longitude: this.editForm.get(['longitude']).value,
      dataCadastro: this.editForm.get(['dataCadastro']).value,
      ativo: this.editForm.get(['ativo']).value,
      polo: this.editForm.get(['polo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocal>>) {
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
