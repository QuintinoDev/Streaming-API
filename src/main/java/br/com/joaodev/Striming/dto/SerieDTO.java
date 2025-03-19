package br.com.joaodev.Striming.dto;

import br.com.joaodev.Striming.model.Categoria;

//Fazendo com oque passe por aq primeiro as informações para serem filtradas e nao aparecer duas coisas
public record SerieDTO(Long id,
                       String titulo,
                       String lancamento,
                       String duracao,
                       Categoria genero,
                       Integer totalDeTemporadas,
                       Double avaliacao,
                       String sinopse,
                       String atores,
                       String poster ){
}
