package br.com.joaodev.Striming.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

//Falando pra meu banco que essa classe é um entidade
@Entity
//Criando a tebela e alterando o nome dela
@Table(name = "episodios")
public class Episodio {
    @Id
    //Falando que meu Id é alto ingrementi
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    @ManyToOne
    private Serie serie;

    public Episodio(){}

    public Episodio(Integer numeroTemporada, DadosEpisodios dadosEpisodios) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodios.titulo();
        this.numeroEpisodio = dadosEpisodios.numero();
        try{
            //O valor vem em String mais estamos falando para ele pegar oq tem de Double dessa string e armazenar em avaliação
            this.avaliacao = Double.valueOf(dadosEpisodios.avaliacao());
            //O valor vem em String mas estamos falando para ele tranforma essa String em local date, caso ele nao consiga vai dar uma exceção.
            this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento());
        }catch (NumberFormatException e){
            this.avaliacao = 0.0;
        }catch (DateTimeParseException e){
            this.dataLancamento = null;
        }
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "temporada: " + temporada +
                ", titulo: " + titulo +
                ", numeroEpisodio: " + numeroEpisodio +
                ", avaliacao:" + avaliacao +
                ", dataLancamento:" + dataLancamento;
    }
}
