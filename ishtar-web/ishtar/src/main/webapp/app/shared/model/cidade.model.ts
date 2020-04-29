import { IParticipante } from 'app/shared/model/participante.model';
import { IPolo } from 'app/shared/model/polo.model';

export const enum Municipio {
  BELEM = 'BELEM',
  CASTANHAL = 'CASTANHAL',
  RECIFE = 'RECIFE'
}

export const enum Estado {
  RO = 'RO',
  AC = 'AC',
  AM = 'AM',
  RR = 'RR',
  PA = 'PA',
  AP = 'AP',
  TO = 'TO',
  MA = 'MA',
  PI = 'PI',
  CE = 'CE',
  RN = 'RN',
  PB = 'PB',
  PE = 'PE',
  AL = 'AL',
  SE = 'SE',
  BA = 'BA',
  MG = 'MG',
  ES = 'ES',
  RJ = 'RJ',
  SP = 'SP',
  PR = 'PR',
  SC = 'SC',
  RS = 'RS',
  MS = 'MS',
  MT = 'MT',
  GO = 'GO',
  DF = 'DF'
}

export interface ICidade {
  id?: number;
  municipio?: Municipio;
  estado?: Estado;
  participantes?: IParticipante[];
  polos?: IPolo[];
}

export class Cidade implements ICidade {
  constructor(
    public id?: number,
    public municipio?: Municipio,
    public estado?: Estado,
    public participantes?: IParticipante[],
    public polos?: IPolo[]
  ) {}
}
