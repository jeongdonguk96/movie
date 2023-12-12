package io.spring.movie.batch.writer;

import io.spring.movie.entity.ActorTemp;
import io.spring.movie.entity.DirectorTemp;
import io.spring.movie.entity.People;
import io.spring.movie.repository.ActorTempRepository;
import io.spring.movie.repository.DirectorTempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@RequiredArgsConstructor
public class PeopleListItemWriter implements ItemWriter<List<People>> {

    private final ActorTempRepository actorTempRepository;
    private final DirectorTempRepository directorTempRepository;

    @Override
    public void write(Chunk<? extends List<People>> chunks) throws Exception {
        for (List<People> chunk : chunks) {
            for (People people : chunk) {
                if (people instanceof ActorTemp) {
                    actorTempRepository.save((ActorTemp) people);
                }
                if (people instanceof DirectorTemp) {
                    directorTempRepository.save((DirectorTemp) people);
                }
            }
        }
    }
}
