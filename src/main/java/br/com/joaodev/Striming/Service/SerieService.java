package br.com.joaodev.Striming.Service;

import br.com.joaodev.Striming.dto.EpisodioDTO;
import br.com.joaodev.Striming.dto.SerieDTO;
import br.com.joaodev.Striming.model.Categoria;
import br.com.joaodev.Striming.model.Serie;
import br.com.joaodev.Striming.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Ondem vamos colocar a funcionalidade da nossa aplicação
@Service
public class SerieService {


    @Autowired
    private SerieRepository repository;

    //Listando as series
    public List<SerieDTO> obterTodasAsSeries(){
        return conversorDados(repository.findAll());
    }

    public List<SerieDTO> obterSeriesTop5(){
        return conversorDados(repository.findTop5ByOrderByAvaliacaoDesc());
    }

    //Padronizando a converção de dados
    private List<SerieDTO> conversorDados(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getLancamento(), s.getDuracao(), s.getGenero(), s.getTotalDeTemporadas(), s.getAvaliacao(), s.getSinopse(), s.getAtores(), s.getPoster()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamento() {
        return conversorDados(repository.lancamentosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getLancamento(), s.getDuracao(), s.getGenero(), s.getTotalDeTemporadas(), s.getAvaliacao(), s.getSinopse(), s.getAtores(), s.getPoster());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasAsTempordas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(),e.getNumeroEpisodio(),e.getTitulo()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterTempordasPorNumero(Long id, Long numero) {
        return repository.obterEpisodiosPorTemporadas(id,numero)
                .stream().map(e -> new EpisodioDTO(e.getTemporada(),e.getNumeroEpisodio(),e.getTitulo()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        return conversorDados(repository.findByGenero(categoria)) ;
    }
}
