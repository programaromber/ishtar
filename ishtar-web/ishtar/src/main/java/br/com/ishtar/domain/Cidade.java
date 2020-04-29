package br.com.ishtar.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.com.ishtar.domain.enumeration.Municipio;

import br.com.ishtar.domain.enumeration.Estado;

/**
 * A Cidade.
 */
@Entity
@Table(name = "cidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "municipio", nullable = false)
    private Municipio municipio;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Participante> participantes = new HashSet<>();

    @OneToMany(mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Polo> polos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public Cidade municipio(Municipio municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Estado getEstado() {
        return estado;
    }

    public Cidade estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<Participante> getParticipantes() {
        return participantes;
    }

    public Cidade participantes(Set<Participante> participantes) {
        this.participantes = participantes;
        return this;
    }

    public Cidade addParticipantes(Participante participante) {
        this.participantes.add(participante);
        participante.setCidade(this);
        return this;
    }

    public Cidade removeParticipantes(Participante participante) {
        this.participantes.remove(participante);
        participante.setCidade(null);
        return this;
    }

    public void setParticipantes(Set<Participante> participantes) {
        this.participantes = participantes;
    }

    public Set<Polo> getPolos() {
        return polos;
    }

    public Cidade polos(Set<Polo> polos) {
        this.polos = polos;
        return this;
    }

    public Cidade addPolos(Polo polo) {
        this.polos.add(polo);
        polo.setCidade(this);
        return this;
    }

    public Cidade removePolos(Polo polo) {
        this.polos.remove(polo);
        polo.setCidade(null);
        return this;
    }

    public void setPolos(Set<Polo> polos) {
        this.polos = polos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cidade)) {
            return false;
        }
        return id != null && id.equals(((Cidade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cidade{" +
            "id=" + getId() +
            ", municipio='" + getMunicipio() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
