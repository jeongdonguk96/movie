package io.spring.movie.batch.peoplejob;

import io.spring.movie.entity.Actor;
import io.spring.movie.entity.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PeopleItemWriter extends JpaItemWriter<Map<String, Object>> {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public void write(Chunk<? extends Map<String, Object>> items) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (Map<String, Object> peopleMap : items) {
            for (String key : peopleMap.keySet()) {
                Object value = peopleMap.get(key);
                if (value instanceof Actor) {
                    System.out.println("배우 저장");
                    entityManager.persist(value);
                } else if(value instanceof Director) {
                    System.out.println("감독 저장");
                    entityManager.persist(value);
                }
            }
        }
    }
}
