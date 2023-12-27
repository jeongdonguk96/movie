package io.spring.movie.batch.peoplejob;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

@Slf4j
@RequiredArgsConstructor
public class PeopleItemWriter extends JpaItemWriter<Object> {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    @Transactional
    public void write(Chunk<?> items) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            for (Object item : items) {
                entityManager.merge(item);
                log.info("item = " + item);
            }

            entityManager.getTransaction().commit();
        }
    }
}
