package io.spring.movie.batch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PeopleResponseDto {

    private PeopleInfoResultDto peopleInfoResult;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PeopleInfoResultDto {
        @JsonProperty("peopleInfo")
        private PeopleInfoDto peopleInfoDto;
        private String dataSource;

        @Data
        public static class PeopleInfoDto {
            @JsonProperty("peopleCd")
            private Long peopleCode;
            @JsonProperty("peopleNm")
            private String peopleName;
            @JsonProperty("sex")
            private String sex;
            @JsonProperty("reoRoleNm")
            private String roleName;
            @JsonProperty("filmos")
            private List<FilmoDto> filmoDto;
        }

        @Data
        public static class FilmoDto {
            @JsonProperty("movieCd")
            private String movieCode;
            @JsonProperty("movieNm")
            private String movieName;
        }
    }
}
