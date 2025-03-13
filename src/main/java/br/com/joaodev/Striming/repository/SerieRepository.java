package br.com.joaodev.Striming.repository;

import br.com.joaodev.Striming.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
