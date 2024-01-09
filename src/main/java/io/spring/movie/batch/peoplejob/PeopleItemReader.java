package io.spring.movie.batch.peoplejob;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.batch.config.ConfigReader;
import io.spring.movie.batch.dto.PeopleRequestDto;
import io.spring.movie.batch.dto.PeopleResponseDto;
import io.spring.movie.batch.dto.PeopleResponseDto.PeopleInfoResultDto;
import io.spring.movie.batch.dto.PeopleResponseDto.PeopleInfoResultDto.PeopleInfoDto;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PeopleItemReader implements ItemReader<PeopleInfoDto> {

    private final PeopleRequestDto peopleRequestDto;
    private final ParsingService parsingService;
    private final ObjectMapper objectMapper;
    private final ConfigReader configReader;

    private List<String> peopleCodes = new ArrayList<>();
    private int readCount = 0;
    private int index = 34427;

    @Override
    public PeopleInfoDto read() throws InterruptedException {
        Thread.sleep(1000);

        // CSV 파일을 읽어와 각각 List에 담는다. Job 최초 read() 시에만 동작한다.
        if (readCount == 0) {
            try {
                Path peopleListCsvPath = Paths.get(configReader.getPeopleApiCsvPath());
                peopleCodes = readCSVFile(peopleListCsvPath);
                readCount++;
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomBatchException("CSV 파일을 읽는 중 에러가 발생했습니다.");
            }
        }

        // CSV 파일에 있는 데이터의 수만큼 API를 요청한다.
        if (readCount <= peopleCodes.size()) {
            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                peopleRequestDto.setPeopleCode(peopleCodes.get(index));
                String url = CustomHttpClientService.buildUrl(peopleRequestDto);
                log.info("URL = " + url);

                HttpGet request = new HttpGet(url);
                try (CloseableHttpResponse response = httpClient.execute(request)) {
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

            index = 0;     // 값 초기화
            readCount = 0; // 값 초기화
            return null; // 요청할 데이터가 없어 Job이 끝남을 의미한다.
        }
    }

    private List<String> readCSVFile(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

        for (String line : lines) {
            String[] data = line.split(",");
            String code = data[0];
            peopleCodes.add(code);
        }
        log.info("CSV 파일의 데이터 수 = " + peopleCodes.size());

        return peopleCodes;
    }

    private PeopleInfoDto returnItem(String responseBody) throws JsonProcessingException {
        PeopleResponseDto peopleResponseDto = parsingService.convertStringToDto(objectMapper, responseBody, PeopleResponseDto.class);
        PeopleInfoResultDto peopleInfoResultDto = peopleResponseDto.getPeopleInfoResult();

        index++;
        return peopleInfoResultDto.getPeopleInfoDto();
    }
}
