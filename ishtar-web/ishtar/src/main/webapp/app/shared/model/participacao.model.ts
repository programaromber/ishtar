import { IParticipante } from 'app/shared/model/participante.model';
import { IEvento } from 'app/shared/model/evento.model';

export interface IParticipacao {
  id?: number;
  nome?: string;
  participante?: IParticipante;
  evento?: IEvento;
}

export class Participacao implements IParticipacao {
  constructor(public id?: number, public nome?: string, public participante?: IParticipante, public evento?: IEvento) {}
}
