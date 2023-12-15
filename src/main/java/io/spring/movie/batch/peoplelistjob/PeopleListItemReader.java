package io.spring.movie.batch.peoplelistjob;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.dto.PeopleListRequestDto;
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
        Thread.sleep(5000);

        if (currentPage <= totalPage + 1) {
            log.info("currentPage = " + currentPage);

            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                peopleRequestDto.setCurPage(String.valueOf(currentPage));
                String url = CustomHttpClientService.buildUrl(peopleRequestDto);
                log.info("URL = " + url);

                HttpGet request = new HttpGet(url);
                List<PeopleDto> peopleDtoList;
                PeopleListResultDto peopleListResultDto;

                try (CloseableHttpResponse response = httpclient.execute(request)) {
                    peopleListResultDto = parsingService.parseJsonToPeopleListResultDto(response, objectMapper);
                    if (peopleListResultDto == null) {
                        throw new CustomApiException("서버 측 API 응답에 에러가 포함되어 있습니다.");
                    }
                    peopleDtoList = peopleListResultDto.getPeopleDtoList();
                    int totalCount = peopleListResultDto.getTotalCount();

                    totalPage = (double) totalCount / 100;
                    if (currentPage == 1) {
                        log.info("totalPage = " + totalPage);
                    }
                    currentPage++; // 다음 페이지로 이동

                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new CustomBatchException("JSON 데이터를 파싱하는 중 에러가 발생했습니다.");
                }

                return peopleDtoList;
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomBatchException("HTTPCLIENT 통신 중 에러가 발생했습니다.");
            }
        } else {
            return null; // 데이터의 끝을 나타냅니다
        }
    }
}