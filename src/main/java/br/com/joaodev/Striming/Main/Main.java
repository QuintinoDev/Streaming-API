package br.com.joaodev.Striming.Main;

import br.com.joaodev.Striming.Service.ConsumindoAPI;
import br.com.joaodev.Striming.Service.ConverteDados;
import br.com.joaodev.Striming.model.DadosEpisodios;
import br.com.joaodev.Striming.model.DadosSerie;
import br.com.joaodev.Striming.model.DadosTemporadas;
import br.com.joaodev.Striming.model.Episodio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

//        Bloco para impreção dos 10 melhores episodios fazendo o java me falar oq ele ta fazendo passo por passo usando o peek.
//        System.out.println("\nTop 10 melhores episódios");
//        //Colocando os dados da lista de episodios em ordem de avaliação, da maior avaliação para a menor.
//        dadosEpisodios.stream()
//                //filtrando todas as avaliações que nao tem nota, e deixando so as melhores avaliadas.
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                //peek serve para mostrar como que o java esta trabalhando, mostra oque ele faz quando damos um comando
//                .peek(e -> System.out.println("Primeiro filtro(N/A) " + e) )
//                //sorted usado para ordenar nossa lista, nesse caso colocamos uma comparação entre as avaliações e mandamos imprimiar da maior para a menor, com o .reversed
//                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
//                .peek(e -> System.out.println("ordenação " + e) )
//                //limit mandando imprimir so as 5 primiras, limitando assim as melhores 5
//                .limit(10)
//                .peek(e -> System.out.println("limite " + e) )
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Letra maiuscula " + e) )
//                .forEach(System.out::println);

        //Criando uma lista de episodios do jeito que definimos na classe episodio, para me mostrar do jeito q foi definido la.
        List<Episodio> episodios = listaDadosTemporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d)))
                .collect(Collectors.toList());

        System.out.println("\n");
        episodios.forEach(System.out::println);

        //Bloco usado para fazermos uma pesquisa por nome de episodio
//        System.out.println("Digite um trecho do titulo do episódio: ");
//        var trechoEpisodio = leitura.nextLine();
//        //Optional é um conteiner usado para guardar uma pesquisa, que estamos fazendo de pesquisar um ep pelo nome e mostrar a temporada
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                //Estou filtrando todos os epsodios para que me mostre somente oq oque o usuario digitou. Como o Optional é baseado no equals de java ele vai procurar somente oq é relamente iqual
//                //por isso que colocamos esse toUppercase no titulo do app e no que o usuario digito para não importar como sera digitado ele vai achar
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoEpisodio.toUpperCase()))
//                //Esse findFirst serve para que ele pegue a primeira pesquisa
//                .findFirst();
//        //condiciona para me mostrar o ep que foi encontrado
//        if (episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado");
//            System.out.println("Temporada: "+episodioBuscado.get().getTemporada() +" Episodio: "+episodioBuscado.get().getTitulo());
//        }else {
//            System.out.println("Episódio não encontrado");
//        }

//        Bloco para pesquisa por data de lançamento de episodio
//        System.out.println("\n Apartir de que ano voce gostaria de ver os episodios");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//        //Variavel para arrumar ano de pesquisa
//        LocalDate dataDeBusca = LocalDate.of(ano,1,1);
//        //Declarando uma classe que arruma as datas para ficar no padrão Brasil
//        DateTimeFormatter fomatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        //Lambida de ordenar por ano de lançamento dos episodios.
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() !=null && e.getDataLancamento().isAfter(dataDeBusca))
//                .forEach(e -> System.out.println("Temporada: " + e.getTemporada() + " Episódio: " + e.getTitulo() + " - Data de Lançamento: " + e.getDataLancamento().format(fomatador)));

        //Bloque que pega a media de avalição por cada temporada, usando o map, para pegar o Integer numero da temporada e o Double para as avaliações
        Map<Integer, Double> avaliaçaoPorTemporada = episodios.stream()
                //filtrando as avaliações para que seja levado em conta somente as que nota acima de 0.0
                .filter(e -> e.getAvaliacao() > 0.0)
                //colotando os dados da temporada
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        //tirando a media das avaliações
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliaçaoPorTemporada);

        //Bloco usado para pegar os dados de todas os meus episódios e me mostrar, usando a classe abaixo, classe usada para pegar dados.
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Media: "+est.getAverage());
        System.out.println("Melhor episódio: "+est.getMax());
        System.out.println("Pior episódio: "+est.getMin());
        System.out.println("Quantidade: "+est.getCount());
    }
}
