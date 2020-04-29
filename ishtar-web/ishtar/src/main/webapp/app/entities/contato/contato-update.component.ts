import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IContato, Contato } from 'app/shared/model/contato.model';
import { ContatoService } from './contato.service';
import { IResponsavel } from 'app/shared/model/responsavel.model';
import { ResponsavelService } from 'app/entities/responsavel';

@Component({
  selector: 'jhi-contato-update',
  templateUrl: './contato-update.component.html'
})
export class ContatoUpdateComponent implements OnInit {
  isSaving: boolean;

  responsavels: IResponsavel[];

  editForm = this.fb.group({
    id: [],
    ddd: [null, [Validators.maxLength(2)]],
    telefone: [null, [Validators.maxLength(10)]],
    responsavel: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contatoService: ContatoService,
    protected responsavelService: ResponsavelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contato }) => {
      this.updateForm(contato);
    });
    this.responsavelService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IResponsavel[]>) => mayBeOk.ok),
        map((response: HttpResponse<IResponsavel[]>) => response.body)
      )
      .subscribe((res: IResponsavel[]) => (this.responsavels = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(contato: IContato) {
    this.editForm.patchValue({
      id: contato.id,
      ddd: contato.ddd,
      telefone: contato.telefone,
      responsavel: contato.responsavel
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contato = this.createFromForm();
    if (contato.id !== undefined) {
      this.subscribeToSaveResponse(this.contatoService.update(contato));
    } else {
      this.subscribeToSaveResponse(this.contatoService.create(contato));
    }
  }

  private createFromForm(): IContato {
    return {
      ...new Contato(),
      id: this.editForm.get(['id']).value,
      ddd: this.editForm.get(['ddd']).value,
      telefone: this.editForm.get(['telefone']).value,
      responsavel: this.editForm.get(['responsavel']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContato>>) {
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

  trackResponsavelById(index: number, item: IResponsavel) {
    return item.id;
  }
}
