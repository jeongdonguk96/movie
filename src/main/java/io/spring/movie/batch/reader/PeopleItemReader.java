package io.spring.movie.batch.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.service.CustomHttpClientService;
import io.spring.movie.batch.service.ParsingService;
import io.spring.movie.dto.PeopleListRequestDto;
import io.spring.movie.dto.PeopleListResponseDto.PeopleListResult.People;
import io.spring.movie.exception.CustomBatchException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.springframework.batch.item.ItemReader;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class PeopleItemReader implements ItemReader<List<People>> {

    private final PeopleListRequestDto peopleRequestDto;
    private final ParsingService parsingService;
    private final ObjectMapper objectMapper;

    @Override
    public List<People> read() {
        System.out.println("reader");
        String url = CustomHttpClientService.buildUriParams(peopleRequestDto);
        List<People> peopleList;

        try (CloseableHttpResponse response = CustomHttpClientService.executeGet(url)) {
            peopleList = parsingService.parseJsonToPeopleListResponseDto(response, objectMapper);

        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBatchException("HTTP GET 요청 중 에러가 발생했습니다.");

        } catch (ParseException e) {
            e.printStackTrace();
            throw new CustomBatchException("JSON 데이터를 파싱하는 중 에러가 발생했습니다.");
        }

        return peopleList;
    }
}
