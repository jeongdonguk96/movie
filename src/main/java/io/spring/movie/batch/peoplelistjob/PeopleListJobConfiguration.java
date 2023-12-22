package io.spring.movie.batch.peoplelistjob;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.config.ConfigReader;
import io.spring.movie.batch.dto.PeopleListRequestDto;
import io.spring.movie.batch.dto.PeopleListResponseDto.PeopleListResultDto.PeopleDto;
import io.spring.movie.batch.service.ParsingService;
import io.spring.movie.batch.temp.PeopleTemp;
import io.spring.movie.exception.CustomApiException;
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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PeopleListJobConfiguration {

    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final ConfigReader configReader;

    private final PeopleListRequestDto peopleRequestDto;
    private final ParsingService parsingService;
    private final ObjectMapper objectMapper;

//    @Bean
    public Job peopleListJob(Step peopleListStep, PeopleListJobListener peopleListJobListener) {
        return new JobBuilder("peopleListJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(peopleListStep)
                .listener(peopleListJobListener)
                .build();
    }

//    @Bean
    public Step peopleListStep(ItemReader<List<PeopleDto>> peopleListItemReader, ItemProcessor<? super List<PeopleTemp>, ?> peopleListItemProcessor, ItemWriter<? super List<PeopleTemp>> peopleListFlatFileItemWriter) {
        return new StepBuilder("peopleStep", jobRepository)
                .chunk(configReader.getPeopleListApiChunk(), transactionManager)
                .reader(peopleListItemReader)
                .processor((ItemProcessor<? super Object, ?>) peopleListItemProcessor)
                .writer((ItemWriter<? super Object>) peopleListFlatFileItemWriter)
                .faultTolerant()
                .skip(CustomApiException.class)
                .skipLimit(configReader.getSkipLimit()/2)
                .build();
    }

    @Bean
    public PeopleListJobListener peopleListJobListener() {
        return new PeopleListJobListener();
    }

//    @Bean
    public ItemReader<List<PeopleDto>> peopleListItemReader() {
        return new PeopleListItemReader(peopleRequestDto, parsingService, objectMapper);
    }

//    @Bean
    public ItemProcessor<? super List<PeopleDto>, ?> peopleListItemProcessor() {
        return new PeopleListItemProcessor();
    }

//    @Bean
    public FlatFileItemWriter<? super List<PeopleTemp>> peopleListFlatFileItemWriter() {
        String directoryPath = "C:\\Users\\nb18-03hp\\csv";
        String filePath = "C:\\Users\\nb18-03hp\\csv\\peopleList.csv";
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
                log.info("존재하지 않는 디렉토리입니다. 디렉토리를 생성합니다.");
            }
        } else {
            log.info("이미 존재하는 디렉토리입니다. 기존 디렉토리를 사용합니다.");
        }
        log.info("디렉토리 경로 = " + directoryPath);
        return new PeopleListFlatFileItemWriter(filePath);
    }
}
