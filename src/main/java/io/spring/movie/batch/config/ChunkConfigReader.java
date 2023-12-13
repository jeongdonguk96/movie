package io.spring.movie.batch.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ChunkConfigReader {
    @Value("${movie.peopleList-api.chunk}")
    private int peopleListApiChunk;
}
