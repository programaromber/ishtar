package br.com.ishtar.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Local.
 */
@Entity
@Table(name = "local")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Local implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "logradouro", length = 80, nullable = false)
    private String logradouro;

    @Size(max = 80)
    @Column(name = "complememto", length = 80)
    private String complememto;

    @NotNull
    @Size(max = 80)
    @Column(name = "bairro", length = 80, nullable = false)
    private String bairro;

    @NotNull
    @Size(max = 8)
    @Column(name = "cep", length = 8, nullable = false)
    private String cep;

    @NotNull
    @Size(max = 8)
    @Column(name = "numero", length = 8, nullable = false)
    private String numero;

    @Column(name = "latitude", precision = 21, scale = 2)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 21, scale = 2)
    private BigDecimal longitude;

    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "local")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Evento> eventos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("locais")
    private Polo polo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Local logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplememto() {
        return complememto;
    }

    public Local complememto(String complememto) {
        this.complememto = complememto;
        return this;
    }

    public void setComplememto(String complememto) {
        this.complememto = complememto;
    }

    public String getBairro() {
        return bairro;
    }

    public Local bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public Local cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public Local numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public Local latitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public Local longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public Local dataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Local ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<Evento> getEventos() {
        return eventos;
    }

    public Local eventos(Set<Evento> eventos) {
        this.eventos = eventos;
        return this;
    }

    public Local addEventos(Evento evento) {
        this.eventos.add(evento);
        evento.setLocal(this);
        return this;
    }

    public Local removeEventos(Evento evento) {
        this.eventos.remove(evento);
        evento.setLocal(null);
        return this;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }

    public Polo getPolo() {
        return polo;
    }

    public Local polo(Polo polo) {
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
        if (!(o instanceof Local)) {
            return false;
        }
        return id != null && id.equals(((Local) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Local{" +
            "id=" + getId() +
            ", logradouro='" + getLogradouro() + "'" +
            ", complememto='" + getComplememto() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cep='" + getCep() + "'" +
            ", numero='" + getNumero() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
