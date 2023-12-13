package io.spring.movie.batch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.dto.PeopleListResponseDto;
import io.spring.movie.batch.dto.PeopleListResponseDto.PeopleListResult;
import io.spring.movie.batch.dto.PeopleListResponseDto.PeopleListResult.PeopleDto;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ParsingService {

    public List<PeopleDto> parseJsonToPeopleListResponseDto(CloseableHttpResponse response, ObjectMapper objectMapper) throws ParseException, IOException {
        int returnStatusCode = response.getCode();
        System.out.println("returnStatusCode = " + returnStatusCode);

        String responseBody = EntityUtils.toString(response.getEntity());
        PeopleListResponseDto peopleResponseDto = objectMapper.readValue(responseBody, PeopleListResponseDto.class);
        PeopleListResult peopleListResult = peopleResponseDto.getPeopleListResult();
        System.out.println("조회 데이터 수 = " + peopleListResult.getTotalCount());

        return peopleListResult.getPeopleDtoList();
    }
}
