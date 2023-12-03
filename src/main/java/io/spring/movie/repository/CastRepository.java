package io.spring.movie.repository;

import io.spring.movie.entity.Cast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastRepository extends JpaRepository<Cast, Long> {
}
