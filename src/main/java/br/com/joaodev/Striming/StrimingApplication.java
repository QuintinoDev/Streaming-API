package br.com.joaodev.Striming;

import br.com.joaodev.Striming.Service.ConsumindoAPI;
import br.com.joaodev.Striming.Service.ConverteDados;
import br.com.joaodev.Striming.model.DadosFilme;
import br.com.joaodev.Striming.model.DadosSerie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//Implementando a interface de linha de comando.
public class StrimingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StrimingApplication.class, args);
	}

	@Override
	//Sobreescrevendo o metodo da interface que estamos implementando.
	public void run(String... args) throws Exception {
		System.out.println("Primeiro projeto spring sem Web");
		//Variavel para quardar chave de acesso da Api
		var apiKey = "";
		//Chamando classe da Api
		var consumindoAPI = new ConsumindoAPI();
		//Declrando variavel json para receber o json do endereço da Api.
		var jsonSerie = consumindoAPI.obterDados("https://www.omdbapi.com/?t=Gilmore+girls"+apiKey);
		var jsonFilme = consumindoAPI.obterDados("https://www.omdbapi.com/?t=matrix"+apiKey);
		//Mostrando oq meinha API chamou com aquele link
		System.out.println(jsonSerie);
		System.out.println(jsonFilme);
		//Chamando minha classe q ordena os meu json do jeito que eu quero
		ConverteDados conversor = new ConverteDados();
		//Chamando record e falando por qual classe que el vai passer e qual o json que ela vai tratar
		DadosSerie dados = conversor.obterDados(jsonSerie, DadosSerie.class);
		System.out.println(dados);
		DadosFilme dadosFilme = conversor.obterDados(jsonFilme, DadosFilme.class);
		System.out.println(dadosFilme);
	}
}
