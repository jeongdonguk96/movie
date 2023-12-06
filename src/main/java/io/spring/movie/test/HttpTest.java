package io.spring.movie.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.config.ConfigReader;
import io.spring.movie.dto.PeopleListRequestDto;
import io.spring.movie.exception.CustomBatchException;
import io.spring.movie.service.CustomHttpClientService;
import io.spring.movie.service.ParsingService;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class HttpTest {

    private final ConfigReader config;
    private final ObjectMapper objectMapper;

    @Bean
    public String test() throws IOException {

        CloseableHttpResponse response = null;

        PeopleListRequestDto peopleRequestDto = PeopleListRequestDto.builder()
                .url("http://www.kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleList.json")
                .key(config.getKey())
                .curPage("1")
                .itemPerPage("10")
                .peopleName("")
                .filmographyName("")
                .build();

        String url = CustomHttpClientService.buildUriParams(peopleRequestDto);

        try {
            response = CustomHttpClientService.executeGet(url);
            ParsingService.parseJsonToPeopleListResponseDto(response, objectMapper);

        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBatchException("HTTP GET 요청 중 에러가 발생했습니다.");

        } catch (ParseException e) {
            e.printStackTrace();
            throw new CustomBatchException("JSON 데이터를 파싱하는 중 에러가 발생했습니다.");
        } finally {
            response.close();
        }

        return "hi";
    }

}
