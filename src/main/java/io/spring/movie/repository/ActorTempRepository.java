package io.spring.movie.repository;

import io.spring.movie.entity.ActorTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorTempRepository extends JpaRepository<ActorTemp, Long> {
}
