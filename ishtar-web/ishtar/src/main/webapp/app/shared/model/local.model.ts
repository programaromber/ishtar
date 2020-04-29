import { Moment } from 'moment';
import { IEvento } from 'app/shared/model/evento.model';
import { IPolo } from 'app/shared/model/polo.model';

export interface ILocal {
  id?: number;
  logradouro?: string;
  complememto?: string;
  bairro?: string;
  cep?: string;
  numero?: string;
  latitude?: number;
  longitude?: number;
  dataCadastro?: Moment;
  ativo?: boolean;
  eventos?: IEvento[];
  polo?: IPolo;
}

export class Local implements ILocal {
  constructor(
    public id?: number,
    public logradouro?: string,
    public complememto?: string,
    public bairro?: string,
    public cep?: string,
    public numero?: string,
    public latitude?: number,
    public longitude?: number,
    public dataCadastro?: Moment,
    public ativo?: boolean,
    public eventos?: IEvento[],
    public polo?: IPolo
  ) {
    this.ativo = this.ativo || false;
  }
}
