package io.spring.movie.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.service.CustomHttpClientService;
import io.spring.movie.batch.service.ParsingService;
import io.spring.movie.dto.PeopleListRequestDto;
import io.spring.movie.dto.PeopleListResponseDto;
import io.spring.movie.exception.CustomBatchException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class HttpTest {

    private final PeopleListRequestDto peopleRequestDto;
    private final ParsingService parsingService;
    private final ObjectMapper objectMapper;

    @Bean
    public String test() {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String url = CustomHttpClientService.buildUriParams(peopleRequestDto);
            HttpGet request = new HttpGet(url); // 예시 URL

            // 요청 보내기
            try (CloseableHttpResponse response = httpclient.execute(request)) {
                // 응답 처리
                int statusCode = response.getCode();
                System.out.println("응답 코드: " + statusCode);

                // 응답 본문 처리
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("응답 본문: " + responseBody);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "hi";
    }

    @Bean
    public String test2() {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String url = CustomHttpClientService.buildUriParams(peopleRequestDto);
            HttpGet request = new HttpGet(url); // 예시 URL

            try (CloseableHttpResponse response = httpclient.execute(request)) {
//            parsingService.parseJsonToPeopleListResponseDto(response, objectMapper);
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
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomBatchException("HTTP GET 요청 중 에러가 발생했습니다.");

            } catch (ParseException e) {
                e.printStackTrace();
                throw new CustomBatchException("JSON 데이터를 파싱하는 중 에러가 발생했습니다.");
            }

            return "hi";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

