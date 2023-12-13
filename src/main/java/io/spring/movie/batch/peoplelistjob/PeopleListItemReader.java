package io.spring.movie.batch.peoplelistjob;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.service.CustomHttpClientService;
import io.spring.movie.batch.service.ParsingService;
import io.spring.movie.batch.dto.PeopleListRequestDto;
import io.spring.movie.batch.dto.PeopleListResponseDto.PeopleListResult.PeopleDto;
import io.spring.movie.exception.CustomBatchException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.springframework.batch.item.ItemReader;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class PeopleListItemReader implements ItemReader<List<PeopleDto>> {

    private final PeopleListRequestDto peopleRequestDto;
    private final ParsingService parsingService;
    private final ObjectMapper objectMapper;

    @Override
    public List<PeopleDto> read() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String url = CustomHttpClientService.buildUrl(peopleRequestDto);
            System.out.println("url = " + url);

            HttpGet request = new HttpGet(url);
            List<PeopleDto> peopleDtoList;

            try (CloseableHttpResponse response = httpclient.execute(request)) {
                peopleDtoList = parsingService.parseJsonToPeopleListResponseDto(response, objectMapper);
                System.out.println("peopleDtoList = " + peopleDtoList);

            } catch (ParseException e) {
                e.printStackTrace();
                throw new CustomBatchException("JSON 데이터를 파싱하는 중 에러가 발생했습니다.");
            }

            return peopleDtoList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBatchException("HTTPCLIENT 통신 중 에러가 발생했습니다.");
        }
    }

//    @Override
//    public List<People> read() {
//        System.out.println("reader");
//        String url = CustomHttpClientService.buildUriParams(peopleRequestDto);
//        List<People> peopleList;
//
//        try (CloseableHttpResponse response = CustomHttpClientService.executeGet(url)) {
//            peopleList = parsingService.parseJsonToPeopleListResponseDto(response, objectMapper);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new CustomBatchException("HTTP GET 요청 중 에러가 발생했습니다.");
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            throw new CustomBatchException("JSON 데이터를 파싱하는 중 에러가 발생했습니다.");
//        }
//
//        return peopleList;
//    }
}
