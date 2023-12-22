package io.spring.movie.batch.peoplelistjob;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.dto.PeopleListRequestDto;
import io.spring.movie.batch.dto.PeopleListResponseDto;
import io.spring.movie.batch.dto.PeopleListResponseDto.PeopleListResultDto;
import io.spring.movie.batch.dto.PeopleListResponseDto.PeopleListResultDto.PeopleDto;
import io.spring.movie.batch.service.CustomHttpClientService;
import io.spring.movie.batch.service.ParsingService;
import io.spring.movie.exception.CustomApiException;
import io.spring.movie.exception.CustomBatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.springframework.batch.item.ItemReader;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PeopleListItemReader implements ItemReader<List<PeopleDto>> {

    private final PeopleListRequestDto peopleRequestDto;
    private final ParsingService parsingService;
    private final ObjectMapper objectMapper;
    private int currentPage = 1;
    private double totalPage = 1;

    @Override
    public List<PeopleDto> read() throws InterruptedException {
        Thread.sleep(1000);

        // 요청 가능한 페이지 수만큼 API를 요청한다.
        if (currentPage <= totalPage + 1) {
            log.info("currentPage = " + currentPage);

            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                peopleRequestDto.setCurPage(String.valueOf(currentPage));
                String url = CustomHttpClientService.buildUrl(peopleRequestDto);
                log.info("URL = " + url);

                HttpGet request = new HttpGet(url);
                try (CloseableHttpResponse response = httpclient.execute(request)) {
                    String responseBody = parsingService.parseJsonToString(response);
                    if (responseBody == null) {
                        throw new CustomApiException("서버 측 API 응답에 에러가 포함되어 있습니다.");
                    }

                    return returnItem(responseBody);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new CustomBatchException("JSON 데이터를 파싱하는 중 에러가 발생했습니다.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomBatchException("HTTPCLIENT 통신 중 에러가 발생했습니다.");
            }
        } else {
            currentPage = 1; // 값 초기화
            return null; // 가져올 데이터가 없어 Job이 끝남을 의미한다.
        }
    }

    private List<PeopleDto> returnItem(String responseBody) throws JsonProcessingException {
        PeopleListResponseDto peopleListResponseDto = parsingService.convertStringToDto(objectMapper, responseBody, PeopleListResponseDto.class);
        PeopleListResultDto peopleListResultDto = peopleListResponseDto.getPeopleListResult();

        int totalCount = peopleListResultDto.getTotalCount();
        totalPage = (double) totalCount / 100;

        if (currentPage == 1) {
            log.info("영화인 목록 API 총 페이지 수 = " + totalPage);
        }

        currentPage++; // 다음 페이지로 이동
        return peopleListResultDto.getPeopleDtoList();
    }
}