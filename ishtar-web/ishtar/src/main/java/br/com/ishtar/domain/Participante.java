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
 * A Participante.
 */
@Entity
@Table(name = "participante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Participante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "email", length = 80, nullable = false)
    private String email;

    @Size(max = 80)
    @Column(name = "nome", length = 80)
    private String nome;

    @Size(max = 2)
    @Column(name = "ddd", length = 2)
    private String ddd;

    @Size(max = 10)
    @Column(name = "telefone", length = 10)
    private String telefone;

    @NotNull
    @Column(name = "notificar", nullable = false)
    private Boolean notificar;

    @NotNull
    @Column(name = "aceito", nullable = false)
    private Boolean aceito;

    @Column(name = "latitude", precision = 21, scale = 2)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 21, scale = 2)
    private BigDecimal longitude;

    @NotNull
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDate dataAtualizacao;

    @OneToMany(mappedBy = "participante")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Participacao> participacoes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("participantes")
    private Cidade cidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Participante email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public Participante nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDdd() {
        return ddd;
    }

    public Participante ddd(String ddd) {
        this.ddd = ddd;
        return this;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    public Participante telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean isNotificar() {
        return notificar;
    }

    public Participante notificar(Boolean notificar) {
        this.notificar = notificar;
        return this;
    }

    public void setNotificar(Boolean notificar) {
        this.notificar = notificar;
    }

    public Boolean isAceito() {
        return aceito;
    }

    public Participante aceito(Boolean aceito) {
        this.aceito = aceito;
        return this;
    }

    public void setAceito(Boolean aceito) {
        this.aceito = aceito;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public Participante latitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public Participante longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public Participante dataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
        return this;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Set<Participacao> getParticipacoes() {
        return participacoes;
    }

    public Participante participacoes(Set<Participacao> participacaos) {
        this.participacoes = participacaos;
        return this;
    }

    public Participante addParticipacoes(Participacao participacao) {
        this.participacoes.add(participacao);
        participacao.setParticipante(this);
        return this;
    }

    public Participante removeParticipacoes(Participacao participacao) {
        this.participacoes.remove(participacao);
        participacao.setParticipante(null);
        return this;
    }

    public void setParticipacoes(Set<Participacao> participacaos) {
        this.participacoes = participacaos;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public Participante cidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Participante)) {
            return false;
        }
        return id != null && id.equals(((Participante) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Participante{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", nome='" + getNome() + "'" +
            ", ddd='" + getDdd() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", notificar='" + isNotificar() + "'" +
            ", aceito='" + isAceito() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", dataAtualizacao='" + getDataAtualizacao() + "'" +
            "}";
    }
}
