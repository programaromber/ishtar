package br.com.ishtar.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Evento.
 */
@Entity
@Table(name = "evento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "titulo", length = 80, nullable = false)
    private String titulo;

    @NotNull
    @Size(max = 255)
    @Column(name = "resumo", length = 255, nullable = false)
    private String resumo;

    @NotNull
    @Size(max = 4000)
    @Column(name = "descricao", length = 4000, nullable = false)
    private String descricao;

    @Size(max = 255)
    @Column(name = "url_imagem", length = 255)
    private String urlImagem;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @Column(name = "data_evento")
    private LocalDate dataEvento;

    @OneToMany(mappedBy = "evento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Participacao> participacoes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("eventos")
    private Agenda agenda;

    @ManyToOne
    @JsonIgnoreProperties("eventos")
    private Local local;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Evento titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public Evento resumo(String resumo) {
        this.resumo = resumo;
        return this;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Evento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public Evento urlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
        return this;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public Evento imagem(byte[] imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return imagemContentType;
    }

    public Evento imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public Evento dataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
        return this;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Set<Participacao> getParticipacoes() {
        return participacoes;
    }

    public Evento participacoes(Set<Participacao> participacaos) {
        this.participacoes = participacaos;
        return this;
    }

    public Evento addParticipacoes(Participacao participacao) {
        this.participacoes.add(participacao);
        participacao.setEvento(this);
        return this;
    }

    public Evento removeParticipacoes(Participacao participacao) {
        this.participacoes.remove(participacao);
        participacao.setEvento(null);
        return this;
    }

    public void setParticipacoes(Set<Participacao> participacaos) {
        this.participacoes = participacaos;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public Evento agenda(Agenda agenda) {
        this.agenda = agenda;
        return this;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Local getLocal() {
        return local;
    }

    public Evento local(Local local) {
        this.local = local;
        return this;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evento)) {
            return false;
        }
        return id != null && id.equals(((Evento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Evento{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", resumo='" + getResumo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", urlImagem='" + getUrlImagem() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", dataEvento='" + getDataEvento() + "'" +
            "}";
    }
}
