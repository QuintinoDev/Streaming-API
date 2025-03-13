package br.com.joaodev.Striming;

import br.com.joaodev.Striming.Main.Main;
import br.com.joaodev.Striming.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//Implementando a interface de linha de comando.
public class StrimingApplication implements CommandLineRunner {
	@Autowired
	private SerieRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(StrimingApplication.class, args);
	}

	@Override
	//Sobreescrevendo o metodo da interface que estamos implementando.
	public void run(String... args) throws Exception {
		Main main =new Main(repositorio);
		main.exibeMenu();
	}
}
