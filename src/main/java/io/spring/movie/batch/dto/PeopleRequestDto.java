package io.spring.movie.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class PeopleRequestDto {
    @Value("${movie.people-api.url}")
    private String url;
    @Value("${movie.people-api.key}")
    private String key;
    @Value("${movie.people-api.peopleCode}")
    private String peopleCode;

}
