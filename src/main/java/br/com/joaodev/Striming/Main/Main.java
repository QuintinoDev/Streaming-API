package br.com.joaodev.Striming.Main;

import br.com.joaodev.Striming.Service.ConsumindoAPI;
import br.com.joaodev.Striming.Service.ConverteDados;
import br.com.joaodev.Striming.model.*;
import br.com.joaodev.Striming.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    //Quando coloco essa final, estou falando que nao vou mudar mais essa variavel, sendo assim tenho que atribui valor e tenho que colocar o nome dela em caixa alta
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("APIKEY_OMDB");
    private Scanner leitura = new Scanner(System.in);
    private ConsumindoAPI consumindoAPI = new ConsumindoAPI();
    private ConverteDados converso = new ConverteDados();
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series = new ArrayList<>();

    public Main(SerieRepository repositorio) {
        this.repositorio =repositorio;
    }


    //Nesse metodo estamos colocando tudo que nosso sistema faz para quando formos para o metodo ruin, so precisar chamar ele.
//    public void exibeMenu1(){
//        System.out.println("Dgite o nome da serie que deseja procurar");
//        var nomeSerie = leitura.nextLine();
//        //Montando endereço de pesquisa da api, com chave nome do filme digitado pelo cliente e link
//        var json = consumindoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
//        DadosSerie dados = converso.obterDados(json, DadosSerie.class);
//        System.out.println(dados+"\n");
//
//        //Lista feita para armazenar todas as temporadas com os dados de cada epsodio da teporada com outra lista q esta em DadosSerie
//        List<DadosTemporadas> listaDadosTemporadas = new ArrayList<>();
//		for (int i =1; i<=dados.totalDeTemporadas(); i++){
//			json = consumindoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+"&season="+ i + API_KEY);
//			DadosTemporadas dadosTemporadas = converso.obterDados(json, DadosTemporadas.class);
//			listaDadosTemporadas.add(dadosTemporadas);
//		}
//        System.out.println("Todas as temporadas com seus episodios:");
//        //Imprimindo a lista
//        listaDadosTemporadas.forEach(System.out::println);
//
//        System.out.println("\nTodos os episodios:");
//        //Lambida. Serve para percorrer pela lista e so pegar dados expecificos que queremos exibir.
//        listaDadosTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//
//        //Pegando todos os epsodios que tem dentro da lista de temporadas e colocando dentro da sua propia lista, criando uma lista so de episodios.
//        List<DadosEpisodios> dadosEpisodios = listaDadosTemporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                //Collecto pode ser representado por tolist tbm, mas ai fica um lista imutavel, que ano pode adicionar mais nenhum elemento, se nao da uma exceção
//                .collect(Collectors.toList());
//
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
//
//        //Criando uma lista de episodios do jeito que definimos na classe episodio, para me mostrar do jeito q foi definido la.
//        List<Episodio> episodios = listaDadosTemporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(d -> new Episodio(t.numeroTemporada(), d)))
//                .collect(Collectors.toList());
//
//        System.out.println("\n");
//        episodios.forEach(System.out::println);
//
//        //Bloco usado para fazermos uma pesquisa por nome de episodio
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
//
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
//
//        //Bloque que pega a media de avalição por cada temporada, usando o map, para pegar o Integer numero da temporada e o Double para as avaliações
//        Map<Integer, Double> avaliaçaoPorTemporada = episodios.stream()
//                //filtrando as avaliações para que seja levado em conta somente as que nota acima de 0.0
//                .filter(e -> e.getAvaliacao() > 0.0)
//                //colotando os dados da temporada
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        //tirando a media das avaliações
//                        Collectors.averagingDouble(Episodio::getAvaliacao)));
//        System.out.println(avaliaçaoPorTemporada);
//
//        //Bloco usado para pegar os dados de todas os meus episódios e me mostrar, usando a classe abaixo, classe usada para pegar dados.
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
//        System.out.println("Media: "+est.getAverage());
//        System.out.println("Melhor episódio: "+est.getMax());
//        System.out.println("Pior episódio: "+est.getMin());
//        System.out.println("Quantidade: "+est.getCount());
//    }
    public void exibeMenu() {
        var opcao = -1;

        while (opcao !=0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar series ja buscadas
                    4 - Buscar serie por títulos
                    5 - Buscar series por ator
                    6 - Buscar top 5 melhores series
                    7 - Series por categoria
                    8 - Buscar serie por quantidade de temporadas
                                        
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            System.out.println("Digite a opção desejada: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    listarTopSeriesMaisBemAvaliadas();
                    break;
                case 7:
                    buscarSeriePorCategoria();
                    break;
                case 8:
                    quantidadeMaximaTemporadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumindoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = converso.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        //colocando metodo de listar series dentro desse metodo para que mostre as series salvas quando chamar o metodo de buscar os eps
        listarSeriesBuscadas();
        System.out.println("Escolha a serie pelo nome: ");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporadas> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumindoAPI.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporadas dadosTemporada = converso.obterDados(json, DadosTemporadas.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numeroTemporada(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }else {
            System.out.println("Serie não encontrada");
        }
    }

    private void listarSeriesBuscadas(){
        //Com o findAll estamos falando que vamos puxar as informações do nosso banco de dados
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha a serie pelo nome: ");
        var nomeSerie = leitura.nextLine();
        //Criando optional para falar que vamos vamos buscar uma serie e pode ser que encontre ou não.
        //repositori usando o metodo que criado na interface, passando oque foi digitado pelo usuario
        Optional<Serie> serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()){
            System.out.println("Dados da serie: "+serieBuscada.get());
        }else {
            System.out.println("Serie não encontrada!");
        }
    }

    private void buscarSeriePorAtor() {
        System.out.println("Digite o nome do ator que deseja buscar: ");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações apartir de que valor: ");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor,avaliacao);
        System.out.println("Series em que "+nomeAtor+" trabalhou.");
        //Passando pela lista e mandando imprimir somente o nome da série e a avaliação.
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo()+" avaliação: "+s.getAvaliacao()));
    }

    private void listarTopSeriesMaisBemAvaliadas() {
        // Puxa todas as séries do banco
        series = repositorio.findAll();

        series.stream()
                // Ordena por avaliação (maior primeiro)
                .sorted(Comparator.comparing(Serie::getAvaliacao).reversed())
                // Pega apenas os primeiros 3 resultados
                .limit(3)
                // Passando pela lista e imprimindo
                .forEach(s -> System.out.println(s.getTitulo()+" avaliação: "+s.getAvaliacao()));
    }

    private void buscarSeriePorCategoria() {
        System.out.println("Digite a categoria desejada: ");
        var nomeCategoria = leitura.nextLine();
        //Pegando oqueo usuario digita passando por dentro do metodo do enum e me dando uma resposta
        Categoria categoria = Categoria.fromPortugues(nomeCategoria);
        List<Serie> seriePorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Series da categoria "+categoria);
        seriePorCategoria.forEach(System.out::println);
    }

    private void quantidadeMaximaTemporadas() {
        System.out.println("Digite a quantidade temporadas desejada: ");
        var quantidadeTemporada = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Digite o nivel de avalição: ");
        var quantidadeAvaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.seriesPorTempordasEAvaliacao(quantidadeTemporada,quantidadeAvaliacao);
        System.out.println("Series Filtradas");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo()+" Temporadas: "+s.getTotalDeTemporadas()+" Avaliação: "+ s.getAvaliacao()));
    }
}