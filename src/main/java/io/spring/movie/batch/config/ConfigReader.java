package io.spring.movie.batch.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ConfigReader {
    @Value("${movie.peopleList-api.chunk}")
    private int peopleListApiChunk;

    @Value("${movie.people-api.chunk}")
    private int peopleApiChunk;

    @Value("${movie.peopleList-api.skipLimit}")
    private int peopleListSkipLimit;

    @Value("${movie.people-api.skipLimit}")
    private int peopleSkipLimit;

    @Value("${movie.people-api.csv-path}")
    private String peopleApiCsvPath;
}
