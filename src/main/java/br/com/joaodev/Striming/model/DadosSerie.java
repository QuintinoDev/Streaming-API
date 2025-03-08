package br.com.joaodev.Striming.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//Colcando o caminha para que o json pegue as informações e coloque nos locais corretos
//JsonAlias vai ler oque vem do json e vai escrever oque colocar na frente como atributo, pode tambem coloar um array de palavras para serem procuradas.
//JsonProperty vai colocar o nome que vem no jason na frente

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias ("Released") String lancamento,
                         @JsonAlias ("Runtime") String duracao,
                         @JsonAlias ("Genre") String genero,
                         @JsonAlias ("totalSeasons") Integer totalDeTemporadas,
                         @JsonAlias ("imdbRating") String avaliacao) {
}
