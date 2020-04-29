package br.com.ishtar.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Agenda.
 */
@Entity
@Table(name = "agenda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 4)
    @Max(value = 4)
    @Column(name = "ano", nullable = false)
    private Integer ano;

    @OneToMany(mappedBy = "agenda")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Evento> eventos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("agendas")
    private Polo polo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public Agenda ano(Integer ano) {
        this.ano = ano;
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Set<Evento> getEventos() {
        return eventos;
    }

    public Agenda eventos(Set<Evento> eventos) {
        this.eventos = eventos;
        return this;
    }

    public Agenda addEventos(Evento evento) {
        this.eventos.add(evento);
        evento.setAgenda(this);
        return this;
    }

    public Agenda removeEventos(Evento evento) {
        this.eventos.remove(evento);
        evento.setAgenda(null);
        return this;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }

    public Polo getPolo() {
        return polo;
    }

    public Agenda polo(Polo polo) {
        this.polo = polo;
        return this;
    }

    public void setPolo(Polo polo) {
        this.polo = polo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agenda)) {
            return false;
        }
        return id != null && id.equals(((Agenda) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Agenda{" +
            "id=" + getId() +
            ", ano=" + getAno() +
            "}";
    }
}
