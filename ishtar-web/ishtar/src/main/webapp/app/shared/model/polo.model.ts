import { ILocal } from 'app/shared/model/local.model';
import { IAgenda } from 'app/shared/model/agenda.model';
import { ICidade } from 'app/shared/model/cidade.model';
import { IResponsavel } from 'app/shared/model/responsavel.model';

export interface IPolo {
  id?: number;
  nome?: string;
  email?: string;
  ddd?: string;
  telefone?: string;
  locais?: ILocal[];
  agendas?: IAgenda[];
  cidade?: ICidade;
  responsavels?: IResponsavel[];
}

export class Polo implements IPolo {
  constructor(
    public id?: number,
    public nome?: string,
    public email?: string,
    public ddd?: string,
    public telefone?: string,
    public locais?: ILocal[],
    public agendas?: IAgenda[],
    public cidade?: ICidade,
    public responsavels?: IResponsavel[]
  ) {}
}
