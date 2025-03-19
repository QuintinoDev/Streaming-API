package br.com.joaodev.Striming.controlller;

import br.com.joaodev.Striming.Service.SerieService;
import br.com.joaodev.Striming.dto.EpisodioDTO;
import br.com.joaodev.Striming.dto.SerieDTO;
import br.com.joaodev.Striming.model.Episodio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Controlador que vai chamar as funcionalidades da nossa aplicação
@RestController
//Deixando padrão começar com o /series
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> obterSeries(){
        return service.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterSeriesTop5(){
        return service.obterSeriesTop5();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamento(){
        return service.obterLancamento();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id){
        return service.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
        return service.obterTodasAsTempordas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumeros(@PathVariable Long id,@PathVariable Long numero){
        return  service.obterTempordasPorNumero(id,numero);
    }

    @GetMapping("/categoria/{nomeGenero}")
    public List<SerieDTO>obterSeriePorCategoria(@PathVariable String nomeGenero){
        return service.obterSeriesPorCategoria(nomeGenero);
    }

}