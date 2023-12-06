package io.spring.movie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.dto.PeopleListResponseDto;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.util.List;

public class ParsingService {

    public static void parseJsonToPeopleListResponseDto(CloseableHttpResponse response, ObjectMapper objectMapper) throws ParseException, IOException {
        int returnStatusCode = response.getCode();
        System.out.println("returnStatusCode = " + returnStatusCode);

        String responseBody = EntityUtils.toString(response.getEntity());

        PeopleListResponseDto peopleResponseDto = objectMapper.readValue(responseBody, PeopleListResponseDto.class);
        PeopleListResponseDto.PeopleListResult peopleListResult = peopleResponseDto.getPeopleListResult();
        System.out.println("조회 데이터 수 = " + peopleListResult.getTotalCount());

        List<PeopleListResponseDto.PeopleListResult.People> peopleList = peopleListResult.getPeopleList();
        for (PeopleListResponseDto.PeopleListResult.People people : peopleList) {
            System.out.println("people = " + people);
        }
    }
}
