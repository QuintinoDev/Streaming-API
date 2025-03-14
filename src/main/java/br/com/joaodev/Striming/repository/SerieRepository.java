package br.com.joaodev.Striming.repository;

import br.com.joaodev.Striming.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    //Estamos passando um parametro em jpa para procurar um serie pelo nome, usando find para procurar o titulo e containing para
    //ver ser tem alguma palavra que o usuario digitou e o ingnorecase para ignorar se é maiuscula ou não
    //Optional de serie é pq pode ser que ele encontre a serie e pode ser que não
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
}
