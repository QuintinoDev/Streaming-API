package br.com.joaodev.Striming.Main;

import br.com.joaodev.Striming.Service.ConsumindoAPI;
import br.com.joaodev.Striming.Service.ConverteDados;
import br.com.joaodev.Striming.model.DadosSerie;
import br.com.joaodev.Striming.model.DadosTemporadas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    //Quando coloco essa final, estou falando que nao vou mudar mais essa variavel, sendo assim tenho que atribui valor e tenho que colocar o nome dela em caixa alta
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=426df5eb";
    private Scanner leitura = new Scanner(System.in);
    private ConsumindoAPI consumindoAPI = new ConsumindoAPI();
    private ConverteDados converso = new ConverteDados();

    public void exibeMenu(){
        System.out.println("Dgite o nome da serie que deseja procurar");
        var nomeSerie = leitura.nextLine();
        var json = consumindoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = converso.obterDados(json, DadosSerie.class);
        System.out.println(dados+"\n");

        List<DadosTemporadas> listaDadosTemporadas = new ArrayList<>();
		for (int i =1; i<=dados.totalDeTemporadas(); i++){
			json = consumindoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+"&season="+ i + API_KEY);
			DadosTemporadas dadosTemporadas = converso.obterDados(json, DadosTemporadas.class);
			listaDadosTemporadas.add(dadosTemporadas);
		}
        listaDadosTemporadas.forEach(System.out::println);

        //Lambida
        listaDadosTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
