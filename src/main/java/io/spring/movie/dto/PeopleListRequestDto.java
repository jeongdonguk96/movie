package io.spring.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class PeopleListRequestDto {
    @Value("${movie.config.url}")
    private String url;
    @Value("${movie.config.key}")
    private String key;
    @Value("${movie.config.curPage}")
    private String curPage;
    @Value("${movie.config.itemPerPage}")
    private String itemPerPage;
    @Value("${movie.config.peopleName}")
    private String peopleName;
    @Value("${movie.config.filmographyName}")
    private String filmographyName;
}
