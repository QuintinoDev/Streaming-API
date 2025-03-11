package br.com.joaodev.Striming.Main;

import br.com.joaodev.Striming.Service.ConsumindoAPI;
import br.com.joaodev.Striming.Service.ConverteDados;
import br.com.joaodev.Striming.model.DadosEpisodios;
import br.com.joaodev.Striming.model.DadosSerie;
import br.com.joaodev.Striming.model.DadosTemporadas;
import br.com.joaodev.Striming.model.Episodio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    //Quando coloco essa final, estou falando que nao vou mudar mais essa variavel, sendo assim tenho que atribui valor e tenho que colocar o nome dela em caixa alta
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=426df5eb";
    private Scanner leitura = new Scanner(System.in);
    private ConsumindoAPI consumindoAPI = new ConsumindoAPI();
    private ConverteDados converso = new ConverteDados();

    //Nesse metodo estamos colocando tudo que nosso sistema faz para quando formos para o metodo ruin, so precisar chamar ele.
    public void exibeMenu(){
        System.out.println("Dgite o nome da serie que deseja procurar");
        var nomeSerie = leitura.nextLine();
        //Montando endereço de pesquisa da api, com chave nome do filme digitado pelo cliente e link
        var json = consumindoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = converso.obterDados(json, DadosSerie.class);
        System.out.println(dados+"\n");

        //Lista feita para armazenar todas as temporadas com os dados de cada epsodio da teporada com outra lista q esta em DadosSerie
        List<DadosTemporadas> listaDadosTemporadas = new ArrayList<>();
		for (int i =1; i<=dados.totalDeTemporadas(); i++){
			json = consumindoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+"&season="+ i + API_KEY);
			DadosTemporadas dadosTemporadas = converso.obterDados(json, DadosTemporadas.class);
			listaDadosTemporadas.add(dadosTemporadas);
		}
        System.out.println("Todas as temporadas com seus episodios:");
        //Imprimindo a lista
        listaDadosTemporadas.forEach(System.out::println);

        System.out.println("\nTodos os episodios:");
        //Lambida. Serve para percorrer pela lista e so pegar dados expecificos que queremos exibir.
        listaDadosTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Pegando todos os epsodios que tem dentro da lista de temporadas e colocando dentro da sua propia lista, criando uma lista so de episodios.
        List<DadosEpisodios> dadosEpisodios = listaDadosTemporadas.stream()
                .flatMap(t -> t.episodios().stream())
                //Collecto pode ser representado por tolist tbm, mas ai fica um lista imutavel, que ano pode adicionar mais nenhum elemento, se nao da uma exceção
                .collect(Collectors.toList());

        System.out.println("\nTop 5 melhores episódios");
        //Colocando os dados dos da lista de episodios em ordem de avaliação, da maior avaliação para a menor.
        dadosEpisodios.stream()
                //filtrando todas as avaliações que nao tem nota, e deixando so as melhores avaliadas.
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                //sorted usado para ordenar nossa lista, nesse caso colocamos uma comparação entre as avaliações e mandamos imprimiar da maior para a menor, com o .reversed
                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
                //limit mandando imprimir so as 5 primiras, limitando assim as melhores 5
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = listaDadosTemporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d)))
                .collect(Collectors.toList());

        System.out.println("\n");
        episodios.forEach(System.out::println);

        System.out.println("\n5 melhores episodios com a temporada:");
        episodios.stream()
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        System.out.println("\n Apartir de que ano voce gostaria de ver os episodios");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataDeBusca = LocalDate.of(ano,1,1);

        DateTimeFormatter fomatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() !=null && e.getDataLancamento().isAfter(dataDeBusca))
                .forEach(e -> System.out.println("Temporada: " + e.getTemporada() + " Episódio: " + e.getTitulo() + " - Data de Lançamento: " + e.getDataLancamento().format(fomatador)));


    }
}
