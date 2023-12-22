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
public class PeopleListRequestDto {
    @Value("${movie.peopleList-api.url}")
    private String url;
    @Value("${movie.peopleList-api.key}")
    private String key;
    @Value("${movie.peopleList-api.curPage}")
    private String curPage;
    @Value("${movie.peopleList-api.itemPerPage}")
    private String itemPerPage;
    @Value("${movie.peopleList-api.peopleName}")
    private String peopleName;
    @Value("${movie.peopleList-api.filmographyName}")
    private String filmographyName;
}
