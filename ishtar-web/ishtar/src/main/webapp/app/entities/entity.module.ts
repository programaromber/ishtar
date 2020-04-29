import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cidade',
        loadChildren: './cidade/cidade.module#IshtarCidadeModule'
      },
      {
        path: 'contato',
        loadChildren: './contato/contato.module#IshtarContatoModule'
      },
      {
        path: 'responsavel',
        loadChildren: './responsavel/responsavel.module#IshtarResponsavelModule'
      },
      {
        path: 'participante',
        loadChildren: './participante/participante.module#IshtarParticipanteModule'
      },
      {
        path: 'polo',
        loadChildren: './polo/polo.module#IshtarPoloModule'
      },
      {
        path: 'local',
        loadChildren: './local/local.module#IshtarLocalModule'
      },
      {
        path: 'agenda',
        loadChildren: './agenda/agenda.module#IshtarAgendaModule'
      },
      {
        path: 'evento',
        loadChildren: './evento/evento.module#IshtarEventoModule'
      },
      {
        path: 'participacao',
        loadChildren: './participacao/participacao.module#IshtarParticipacaoModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IshtarEntityModule {}
