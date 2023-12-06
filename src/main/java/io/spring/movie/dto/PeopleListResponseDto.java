package io.spring.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PeopleListResponseDto {

    private PeopleListResult peopleListResult;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PeopleListResult {
        @JsonProperty("totCnt")
        private int totalCount;
        private List<People> peopleList;
        private String dataSource;

        @Data
        public static class People {
            @JsonProperty("peopleCd")
            private String peopleCode;
            @JsonProperty("peopleNm")
            private String peopleName;
            @JsonProperty("repRoleNm")
            private String representRoleName;
        }
    }
}
