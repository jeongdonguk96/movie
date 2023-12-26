package io.spring.movie.batch.peoplejob;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.config.ConfigReader;
import io.spring.movie.batch.dto.PeopleRequestDto;
import io.spring.movie.batch.dto.PeopleResponseDto.PeopleInfoResultDto.PeopleInfoDto;
import io.spring.movie.batch.listener.CustomJobListener;
import io.spring.movie.batch.service.ParsingService;
import io.spring.movie.exception.CustomApiException;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PeopleJobConfiguration {

    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final ConfigReader configReader;

    private final PeopleRequestDto peopleRequestDto;
    private final ParsingService parsingService;
    private final ObjectMapper objectMapper;

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job peopleJob(Step peopleStep, CustomJobListener peopleJobListener) {
        return new JobBuilder("peopleJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(peopleStep)
                .listener(peopleJobListener)
                .build();
    }

    @Bean
    public CustomJobListener peopleJobListener() {
        return new CustomJobListener();
    }

    @Bean
    public Step peopleStep(ItemReader<PeopleInfoDto> peopleItemReader, ItemProcessor<? super PeopleInfoDto, ?> peopleItemProcessor, ItemWriter<? super Object> peopleItemWriter) {
        return new StepBuilder("peopleStep", jobRepository)
                .chunk(configReader.getPeopleApiChunk(), transactionManager)
                .reader(peopleItemReader)
                .processor((ItemProcessor<? super Object, ?>) peopleItemProcessor)
                .writer(peopleItemWriter)
                .faultTolerant()
                .skip(CustomApiException.class)
                .skipLimit(configReader.getPeopleSkipLimit())
                .build();
    }

    @Bean
    public ItemReader<PeopleInfoDto> peopleItemReader() {
        return new PeopleItemReader(peopleRequestDto, parsingService, objectMapper, configReader);
    }

    @Bean
    public ItemProcessor<? super PeopleInfoDto, ?> peopleItemProcessor() {
        return new PeopleItemProcessor();
    }

    @Bean
    public JpaItemWriter<Object> peopleItemWriter() {
        PeopleItemWriter itemWriter = new PeopleItemWriter(entityManagerFactory);
        itemWriter.setEntityManagerFactory(entityManagerFactory);

        return itemWriter;
    }
}
