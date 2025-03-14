package br.com.joaodev.Striming.model;
import br.com.joaodev.Striming.Service.ConsultarChatGpt;
import jakarta.persistence.*;
import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

//Nessa anotação estou falando que essa classe é um tabela do meu bando de dados
@Entity
//Essa anotação serve para renomear o nome da minha tabela no meu banco de dados, pq ele cria o nome da tabela de acordo com o nome da classe
@Table(name = "series")
public class Serie {
    //Definindo meu Id
    @Id
    //Falando que meu Id é alto ingrementi
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Essa anotação tem varios atributos um deles é esse de falar que ano pode repetir o nome e outro é pra alterar o nome da coluna no banco de dados
    @Column(unique = true)
    private String titulo;
    private String lancamento;
    private String duracao;
    //Falando que minha coluna é um enum, do tipo String
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private Integer totalDeTemporadas;
    private Double avaliacao;
    private String sinopse;
    private String atores;
    private String poster;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    public Serie() {}

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.lancamento = dadosSerie.lancamento();
        this.duracao = dadosSerie.duracao();
        //Usando o Enum vamos pegar o metodo que fizesmo la e passar por aq, como vem mais de uma categoria la, usamos o split para separar os generos e pegar somente o primeiro
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.totalDeTemporadas = dadosSerie.totalDeTemporadas();
        //Forma de tentar atribuir um valor double vindo de uma string, caso nao consiga atribui 0
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        //Aqui se voce pegar uma licença da api do chatGpt poderar usar o comando ConsultarChatGpt.obterTraducao(dadosSerie.sinopse().trim()) para fazer a trdução
        this.sinopse = dadosSerie.sinopse();
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e-> e.setSerie(this));
        this.episodios = episodios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", lancamento='" + lancamento + '\'' +
                ", duracao='" + duracao + '\'' +
                ", totalDeTemporadas=" + totalDeTemporadas +
                ", avaliacao=" + avaliacao +
                ", sinopse='" + sinopse + '\'' +
                ", atores='" + atores + '\'' +
                ", poster='" + poster + '\'' +
                ", epsodios='" + episodios + '\'';
    }
}
