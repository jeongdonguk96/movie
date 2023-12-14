package io.spring.movie.batch.peoplelistjob;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.config.ChunkConfigReader;
import io.spring.movie.batch.dto.PeopleListRequestDto;
import io.spring.movie.batch.dto.PeopleListResponseDto.PeopleListResultDto.PeopleDto;
import io.spring.movie.batch.service.ParsingService;
import io.spring.movie.entity.People;
import io.spring.movie.repository.ActorTempRepository;
import io.spring.movie.repository.DirectorTempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PeopleListJobConfiguration {

    private final PlatformTransactionManager transactionManager;
    private final ChunkConfigReader chunkConfigReader;
    private final JobRepository jobRepository;

    private final DirectorTempRepository directorTempRepository;
    private final ActorTempRepository actorTempRepository;

    private final PeopleListRequestDto peopleRequestDto;
    private final ParsingService parsingService;
    private final ObjectMapper objectMapper;

    @Bean
    public Job peopleListJob(Step peopleListStep) {
        return new JobBuilder("peopleListJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(peopleListStep)
//                .listener()
                .build();
    }

    @Bean
    public Step peopleListStep(ItemReader<List<PeopleDto>> peopleListItemReader, ItemProcessor<? super List<People>, ?> peopleListItemProcessor, ItemWriter<? super List<People>> peopleListItemWriter) {
        return new StepBuilder("peopleStep", jobRepository)
                .chunk(chunkConfigReader.getPeopleListApiChunk(), transactionManager)
                .reader(peopleListItemReader)
                .processor((ItemProcessor<? super Object, ?>) peopleListItemProcessor)
                .writer((ItemWriter<? super Object>) peopleListItemWriter)
                .build();
    }

    @Bean
    public ItemReader<List<PeopleDto>> peopleListItemReader() {
        return new PeopleListItemReader(peopleRequestDto, parsingService, objectMapper);
    }

    @Bean
    public ItemProcessor<? super List<PeopleDto>, ?> peopleListItemProcessor() {
        return new PeopleListItemProcessor();
    }

    @Bean
    public ItemWriter<? super List<People>> peopleListItemWriter() {
        return new PeopleListItemWriter(actorTempRepository, directorTempRepository);
    }

}
