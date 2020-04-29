import { IEvento } from 'app/shared/model/evento.model';
import { IPolo } from 'app/shared/model/polo.model';

export interface IAgenda {
  id?: number;
  ano?: number;
  eventos?: IEvento[];
  polo?: IPolo;
}

export class Agenda implements IAgenda {
  constructor(public id?: number, public ano?: number, public eventos?: IEvento[], public polo?: IPolo) {}
}
