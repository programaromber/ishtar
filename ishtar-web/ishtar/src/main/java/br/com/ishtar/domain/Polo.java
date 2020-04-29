package br.com.ishtar.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Polo.
 */
@Entity
@Table(name = "polo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Polo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "nome", length = 80, nullable = false)
    private String nome;

    @Size(max = 80)
    @Column(name = "email", length = 80)
    private String email;

    @Size(max = 2)
    @Column(name = "ddd", length = 2)
    private String ddd;

    @Size(max = 10)
    @Column(name = "telefone", length = 10)
    private String telefone;

    @OneToMany(mappedBy = "polo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Local> locais = new HashSet<>();

    @OneToMany(mappedBy = "polo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Agenda> agendas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("polos")
    private Cidade cidade;

    @ManyToMany(mappedBy = "polos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Responsavel> responsavels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Polo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public Polo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDdd() {
        return ddd;
    }

    public Polo ddd(String ddd) {
        this.ddd = ddd;
        return this;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    public Polo telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<Local> getLocais() {
        return locais;
    }

    public Polo locais(Set<Local> locals) {
        this.locais = locals;
        return this;
    }

    public Polo addLocais(Local local) {
        this.locais.add(local);
        local.setPolo(this);
        return this;
    }

    public Polo removeLocais(Local local) {
        this.locais.remove(local);
        local.setPolo(null);
        return this;
    }

    public void setLocais(Set<Local> locals) {
        this.locais = locals;
    }

    public Set<Agenda> getAgendas() {
        return agendas;
    }

    public Polo agendas(Set<Agenda> agenda) {
        this.agendas = agenda;
        return this;
    }

    public Polo addAgendas(Agenda agenda) {
        this.agendas.add(agenda);
        agenda.setPolo(this);
        return this;
    }

    public Polo removeAgendas(Agenda agenda) {
        this.agendas.remove(agenda);
        agenda.setPolo(null);
        return this;
    }

    public void setAgendas(Set<Agenda> agenda) {
        this.agendas = agenda;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public Polo cidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Set<Responsavel> getResponsavels() {
        return responsavels;
    }

    public Polo responsavels(Set<Responsavel> responsavels) {
        this.responsavels = responsavels;
        return this;
    }

    public Polo addResponsavel(Responsavel responsavel) {
        this.responsavels.add(responsavel);
        responsavel.getPolos().add(this);
        return this;
    }

    public Polo removeResponsavel(Responsavel responsavel) {
        this.responsavels.remove(responsavel);
        responsavel.getPolos().remove(this);
        return this;
    }

    public void setResponsavels(Set<Responsavel> responsavels) {
        this.responsavels = responsavels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Polo)) {
            return false;
        }
        return id != null && id.equals(((Polo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Polo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", ddd='" + getDdd() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
