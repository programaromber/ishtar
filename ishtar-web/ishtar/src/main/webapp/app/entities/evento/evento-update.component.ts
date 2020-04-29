import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEvento, Evento } from 'app/shared/model/evento.model';
import { EventoService } from './evento.service';
import { IAgenda } from 'app/shared/model/agenda.model';
import { AgendaService } from 'app/entities/agenda';
import { ILocal } from 'app/shared/model/local.model';
import { LocalService } from 'app/entities/local';

@Component({
  selector: 'jhi-evento-update',
  templateUrl: './evento-update.component.html'
})
export class EventoUpdateComponent implements OnInit {
  isSaving: boolean;

  agenda: IAgenda[];

  locals: ILocal[];
  dataEventoDp: any;

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required, Validators.maxLength(80)]],
    resumo: [null, [Validators.required, Validators.maxLength(255)]],
    descricao: [null, [Validators.required, Validators.maxLength(4000)]],
    urlImagem: [null, [Validators.maxLength(255)]],
    imagem: [],
    imagemContentType: [],
    dataEvento: [],
    agenda: [],
    local: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected eventoService: EventoService,
    protected agendaService: AgendaService,
    protected localService: LocalService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ evento }) => {
      this.updateForm(evento);
    });
    this.agendaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgenda[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgenda[]>) => response.body)
      )
      .subscribe((res: IAgenda[]) => (this.agenda = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.localService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILocal[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocal[]>) => response.body)
      )
      .subscribe((res: ILocal[]) => (this.locals = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(evento: IEvento) {
    this.editForm.patchValue({
      id: evento.id,
      titulo: evento.titulo,
      resumo: evento.resumo,
      descricao: evento.descricao,
      urlImagem: evento.urlImagem,
      imagem: evento.imagem,
      imagemContentType: evento.imagemContentType,
      dataEvento: evento.dataEvento,
      agenda: evento.agenda,
      local: evento.local
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const evento = this.createFromForm();
    if (evento.id !== undefined) {
      this.subscribeToSaveResponse(this.eventoService.update(evento));
    } else {
      this.subscribeToSaveResponse(this.eventoService.create(evento));
    }
  }

  private createFromForm(): IEvento {
    return {
      ...new Evento(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      resumo: this.editForm.get(['resumo']).value,
      descricao: this.editForm.get(['descricao']).value,
      urlImagem: this.editForm.get(['urlImagem']).value,
      imagemContentType: this.editForm.get(['imagemContentType']).value,
      imagem: this.editForm.get(['imagem']).value,
      dataEvento: this.editForm.get(['dataEvento']).value,
      agenda: this.editForm.get(['agenda']).value,
      local: this.editForm.get(['local']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvento>>) {
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

  trackAgendaById(index: number, item: IAgenda) {
    return item.id;
  }

  trackLocalById(index: number, item: ILocal) {
    return item.id;
  }
}
