package io.spring.movie.repository;

import io.spring.movie.entity.BoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxOfficeRepository extends JpaRepository<BoxOffice, Long> {
}
