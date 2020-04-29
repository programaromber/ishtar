import { IResponsavel } from 'app/shared/model/responsavel.model';

export interface IContato {
  id?: number;
  ddd?: string;
  telefone?: string;
  responsavel?: IResponsavel;
}

export class Contato implements IContato {
  constructor(public id?: number, public ddd?: string, public telefone?: string, public responsavel?: IResponsavel) {}
}
