package io.spring.movie.repository;

import io.spring.movie.entity.DirectorTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorTempRepository extends JpaRepository<DirectorTemp, Long> {
}
