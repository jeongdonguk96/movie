package io.spring.movie.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ParsingService {

    public String parseJsonToString(CloseableHttpResponse response) throws ParseException, IOException {
        int returnStatusCode = response.getCode();
        log.info("returnStatusCode = " + returnStatusCode);

        String responseBody = EntityUtils.toString(response.getEntity());
        if (responseBody.isEmpty() || responseBody.contains("\"faultInfo\":")) {
            log.info("null값 리턴됨");
            return null;
        }

        return responseBody;
    }

    public <T> T convertStringToDto(ObjectMapper objectMapper, String jsonString, Class<T> dtoClass) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, dtoClass);
    }
}
