import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPolo, Polo } from 'app/shared/model/polo.model';
import { PoloService } from './polo.service';
import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from 'app/entities/cidade';
import { IResponsavel } from 'app/shared/model/responsavel.model';
import { ResponsavelService } from 'app/entities/responsavel';

@Component({
  selector: 'jhi-polo-update',
  templateUrl: './polo-update.component.html'
})
export class PoloUpdateComponent implements OnInit {
  isSaving: boolean;

  cidades: ICidade[];

  responsavels: IResponsavel[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(80)]],
    email: [null, [Validators.maxLength(80)]],
    ddd: [null, [Validators.maxLength(2)]],
    telefone: [null, [Validators.maxLength(10)]],
    cidade: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected poloService: PoloService,
    protected cidadeService: CidadeService,
    protected responsavelService: ResponsavelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ polo }) => {
      this.updateForm(polo);
    });
    this.cidadeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICidade[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICidade[]>) => response.body)
      )
      .subscribe((res: ICidade[]) => (this.cidades = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.responsavelService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IResponsavel[]>) => mayBeOk.ok),
        map((response: HttpResponse<IResponsavel[]>) => response.body)
      )
      .subscribe((res: IResponsavel[]) => (this.responsavels = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(polo: IPolo) {
    this.editForm.patchValue({
      id: polo.id,
      nome: polo.nome,
      email: polo.email,
      ddd: polo.ddd,
      telefone: polo.telefone,
      cidade: polo.cidade
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const polo = this.createFromForm();
    if (polo.id !== undefined) {
      this.subscribeToSaveResponse(this.poloService.update(polo));
    } else {
      this.subscribeToSaveResponse(this.poloService.create(polo));
    }
  }

  private createFromForm(): IPolo {
    return {
      ...new Polo(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      email: this.editForm.get(['email']).value,
      ddd: this.editForm.get(['ddd']).value,
      telefone: this.editForm.get(['telefone']).value,
      cidade: this.editForm.get(['cidade']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPolo>>) {
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

  trackResponsavelById(index: number, item: IResponsavel) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
