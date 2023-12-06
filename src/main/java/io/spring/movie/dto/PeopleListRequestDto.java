package io.spring.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PeopleListRequestDto {
    private String url;
    private String key;
    private String curPage;
    private String itemPerPage;
    private String peopleName;
    private String filmographyName;
}
