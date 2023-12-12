package io.spring.movie.batch.writer;

import io.spring.movie.entity.People;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class PeopleListItemWriter implements ItemWriter<People> {

    @Override
    public void write(Chunk<? extends People> chunk) throws Exception {

    }
}
