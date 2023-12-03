package io.spring.movie.repository;

import io.spring.movie.entity.Filmography;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmographyRepository extends JpaRepository<Filmography, Long> {
}
