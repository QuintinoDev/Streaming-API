package br.com.joaodev.Striming.repository;

import br.com.joaodev.Striming.model.Categoria;
import br.com.joaodev.Striming.model.Episodio;
import br.com.joaodev.Striming.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    //Estamos passando um parametro em jpa para procurar um serie pelo nome, usando find para procurar o titulo e containing para
    //ver ser tem alguma palavra que o usuario digitou e o ingnorecase para ignorar se é maiuscula ou não
    //Optional de serie é pq pode ser que ele encontre a serie e pode ser que não
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    //Metodo criado para pesquisa de atores e gerar lista com series onde eles participam
    //AndavaliacaoGreaterThanEqual parametro para falar que queremos mostrar somente aquelas series que são melhores avaliadas
    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);
    //feito para buscar o genero que eu quero e mes mostras quais series estão salvas com aquele genero no meu banco
    List<Serie> findByGenero(Categoria categoria);
    List<Serie> findTop5ByOrderByAvaliacaoDesc();
    //Pegando total de temporadas e mostrando somente quem tem aquele quando ou mais, e pegando as avaliações e mostrando quem tem aquela quantidade ou mais
    List<Serie> findBytotalDeTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer maxTemporadas, Double avaliacao);
    //Faz a mesma coisa do de cima mas aq estamos usando JPQL que fica um pouco mais claro oque estamos fazendo, quase mesmo codigo usado para pesquisa no propio banco
    //Chamamos Query para passarmos nosso parametro, colocamos a variavel com dois pontos na frente para falar que é variavel, e temos que declarar noss classe
    //Chamando cada coisa que queremos de dentro da nossa classe
    @Query("select s from Serie s WHERE s.totalDeTemporadas <= :maxTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTempordasEAvaliacao(Integer maxTemporadas, Double avaliacao);

    //Buscando episodios por trecho de nomes
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodiosPortrecho(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodisoPorSerieEAno(Serie serie, int anoLeitura);

    List<Serie> findTop5ByOrderByEpisodiosDataLancamentoDesc();

    @Query("SELECT s FROM Serie s " +
            "JOIN s.episodios e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> lancamentosMaisRecentes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numero")
    List<Episodio> obterEpisodiosPorTemporadas(Long id, Long numero);
}
