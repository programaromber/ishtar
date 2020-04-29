import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IResponsavel, Responsavel } from 'app/shared/model/responsavel.model';
import { ResponsavelService } from './responsavel.service';
import { IUser, UserService } from 'app/core';
import { IPolo } from 'app/shared/model/polo.model';
import { PoloService } from 'app/entities/polo';

@Component({
  selector: 'jhi-responsavel-update',
  templateUrl: './responsavel-update.component.html'
})
export class ResponsavelUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  polos: IPolo[];
  dataCadastroDp: any;

  editForm = this.fb.group({
    id: [],
    dataCadastro: [null, [Validators.required]],
    ativo: [null, [Validators.required]],
    user: [],
    polos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected responsavelService: ResponsavelService,
    protected userService: UserService,
    protected poloService: PoloService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ responsavel }) => {
      this.updateForm(responsavel);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.poloService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPolo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPolo[]>) => response.body)
      )
      .subscribe((res: IPolo[]) => (this.polos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(responsavel: IResponsavel) {
    this.editForm.patchValue({
      id: responsavel.id,
      dataCadastro: responsavel.dataCadastro,
      ativo: responsavel.ativo,
      user: responsavel.user,
      polos: responsavel.polos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const responsavel = this.createFromForm();
    if (responsavel.id !== undefined) {
      this.subscribeToSaveResponse(this.responsavelService.update(responsavel));
    } else {
      this.subscribeToSaveResponse(this.responsavelService.create(responsavel));
    }
  }

  private createFromForm(): IResponsavel {
    return {
      ...new Responsavel(),
      id: this.editForm.get(['id']).value,
      dataCadastro: this.editForm.get(['dataCadastro']).value,
      ativo: this.editForm.get(['ativo']).value,
      user: this.editForm.get(['user']).value,
      polos: this.editForm.get(['polos']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsavel>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackPoloById(index: number, item: IPolo) {
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
