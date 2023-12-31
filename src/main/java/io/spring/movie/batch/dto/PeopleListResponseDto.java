package io.spring.movie.batch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PeopleListResponseDto {

    private PeopleListResultDto peopleListResult;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PeopleListResultDto {
        @JsonProperty("totCnt")
        private int totalCount;
        @JsonProperty("peopleList")
        private List<PeopleDto> peopleDtoList;
        private String dataSource;

        @Data
        public static class PeopleDto {
            @JsonProperty("peopleCd")
            private String peopleCode;
            @JsonProperty("peopleNm")
            private String peopleName;
            @JsonProperty("repRoleNm")
            private String representRoleName;
        }
    }
}
