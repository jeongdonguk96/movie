package io.spring.movie.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.movie.dto.PeopleListRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HttpTest {

    private final PeopleListRequestDto peopleRequestDto;
    private final ObjectMapper objectMapper;

//    @Bean
//    public String test() {
//
//        String url = CustomHttpClientService.buildUriParams(peopleRequestDto);
//
//        try (CloseableHttpResponse response = CustomHttpClientService.executeGet(url)) {
//            ParsingService.parseJsonToPeopleListResponseDto(response, objectMapper);
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
//        return "hi";
//    }

}
//    @Bean
//    public String test() throws IOException {
//
//        CloseableHttpResponse response = null;
//        String url = CustomHttpClientService.buildUriParams(peopleRequestDto);
//
//        try {
//            response = CustomHttpClientService.executeGet(url);
//            ParsingService.parseJsonToPeopleListResponseDto(response, objectMapper);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new CustomBatchException("HTTP GET 요청 중 에러가 발생했습니다.");
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            throw new CustomBatchException("JSON 데이터를 파싱하는 중 에러가 발생했습니다.");
//        } finally {
//            response.close();
//        }
//
//        return "hi";
//    }
