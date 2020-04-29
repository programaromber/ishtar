import { Moment } from 'moment';
import { IParticipacao } from 'app/shared/model/participacao.model';
import { ICidade } from 'app/shared/model/cidade.model';

export interface IParticipante {
  id?: number;
  email?: string;
  nome?: string;
  ddd?: string;
  telefone?: string;
  notificar?: boolean;
  aceito?: boolean;
  latitude?: number;
  longitude?: number;
  dataAtualizacao?: Moment;
  participacoes?: IParticipacao[];
  cidade?: ICidade;
}

export class Participante implements IParticipante {
  constructor(
    public id?: number,
    public email?: string,
    public nome?: string,
    public ddd?: string,
    public telefone?: string,
    public notificar?: boolean,
    public aceito?: boolean,
    public latitude?: number,
    public longitude?: number,
    public dataAtualizacao?: Moment,
    public participacoes?: IParticipacao[],
    public cidade?: ICidade
  ) {
    this.notificar = this.notificar || false;
    this.aceito = this.aceito || false;
  }
}
