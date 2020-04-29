package br.com.ishtar.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Responsavel.
 */
@Entity
@Table(name = "responsavel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Responsavel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "responsavel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contato> contatos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "responsavel_polos",
               joinColumns = @JoinColumn(name = "responsavel_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "polos_id", referencedColumnName = "id"))
    private Set<Polo> polos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public Responsavel dataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Responsavel ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public User getUser() {
        return user;
    }

    public Responsavel user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Contato> getContatos() {
        return contatos;
    }

    public Responsavel contatos(Set<Contato> contatoes) {
        this.contatos = contatoes;
        return this;
    }

    public Responsavel addContatos(Contato contato) {
        this.contatos.add(contato);
        contato.setResponsavel(this);
        return this;
    }

    public Responsavel removeContatos(Contato contato) {
        this.contatos.remove(contato);
        contato.setResponsavel(null);
        return this;
    }

    public void setContatos(Set<Contato> contatoes) {
        this.contatos = contatoes;
    }

    public Set<Polo> getPolos() {
        return polos;
    }

    public Responsavel polos(Set<Polo> polos) {
        this.polos = polos;
        return this;
    }

    public Responsavel addPolos(Polo polo) {
        this.polos.add(polo);
        polo.getResponsavels().add(this);
        return this;
    }

    public Responsavel removePolos(Polo polo) {
        this.polos.remove(polo);
        polo.getResponsavels().remove(this);
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
        if (!(o instanceof Responsavel)) {
            return false;
        }
        return id != null && id.equals(((Responsavel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Responsavel{" +
            "id=" + getId() +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
