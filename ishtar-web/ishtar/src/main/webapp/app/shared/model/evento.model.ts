import { Moment } from 'moment';
import { IParticipacao } from 'app/shared/model/participacao.model';
import { IAgenda } from 'app/shared/model/agenda.model';
import { ILocal } from 'app/shared/model/local.model';

export interface IEvento {
  id?: number;
  titulo?: string;
  resumo?: string;
  descricao?: string;
  urlImagem?: string;
  imagemContentType?: string;
  imagem?: any;
  dataEvento?: Moment;
  participacoes?: IParticipacao[];
  agenda?: IAgenda;
  local?: ILocal;
}

export class Evento implements IEvento {
  constructor(
    public id?: number,
    public titulo?: string,
    public resumo?: string,
    public descricao?: string,
    public urlImagem?: string,
    public imagemContentType?: string,
    public imagem?: any,
    public dataEvento?: Moment,
    public participacoes?: IParticipacao[],
    public agenda?: IAgenda,
    public local?: ILocal
  ) {}
}
