package io.spring.movie.batch.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.reader.PeopleItemReader;
import io.spring.movie.batch.service.ParsingService;
import io.spring.movie.dto.PeopleListRequestDto;
import io.spring.movie.dto.PeopleListResponseDto.PeopleListResult.People;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PeopleJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    private final PeopleListRequestDto peopleRequestDto;
    private final ParsingService parsingService;
    private final ObjectMapper objectMapper;

    @Bean
    public Job peopleJob(Step peopleStep) {
        System.out.println("peopleJob = ");
        return new JobBuilder("peopleJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(peopleStep)
//                .listener()
                .build();
    }

    @Bean
    public Step peopleStep(ItemReader<List<People>> peopleItemReader) {
        System.out.println("peopleStep = ");
        return new StepBuilder("peopleStep", jobRepository)
                .chunk(100, transactionManager)
                .reader(peopleItemReader)
                .writer(chunk -> {
                    System.out.println("hi");
                })
                .build();
    }

    @Bean
    public ItemReader<List<People>> peopleItemReader() {
        return new PeopleItemReader(peopleRequestDto, parsingService, objectMapper);
    }

}
