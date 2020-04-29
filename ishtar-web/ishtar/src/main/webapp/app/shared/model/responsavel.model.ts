import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IContato } from 'app/shared/model/contato.model';
import { IPolo } from 'app/shared/model/polo.model';

export interface IResponsavel {
  id?: number;
  dataCadastro?: Moment;
  ativo?: boolean;
  user?: IUser;
  contatos?: IContato[];
  polos?: IPolo[];
}

export class Responsavel implements IResponsavel {
  constructor(
    public id?: number,
    public dataCadastro?: Moment,
    public ativo?: boolean,
    public user?: IUser,
    public contatos?: IContato[],
    public polos?: IPolo[]
  ) {
    this.ativo = this.ativo || false;
  }
}
