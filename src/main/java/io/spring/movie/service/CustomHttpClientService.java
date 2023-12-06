package io.spring.movie.service;

import io.spring.movie.dto.PeopleListRequestDto;
import io.spring.movie.exception.CustomBatchException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Service
public class CustomHttpClientService {

    public static CloseableHttpResponse executeGet(String url) throws IOException {

        if (url == null) {
            throw new CustomBatchException("URL은 NULL일 수 없습니다.");
        }

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);

        try {
            httpClient = HttpClients.createDefault();
            response = httpClient.execute(httpGet);

        } finally {
            Objects.requireNonNull(httpClient).close();

        }

        return response;
    }

    public static String buildUriParams(PeopleListRequestDto peopleRequestDto) {

        try {
            URIBuilder uriBuilder = new URIBuilder(peopleRequestDto.getUrl());
            uriBuilder.setParameter("key", peopleRequestDto.getKey());
            uriBuilder.setParameter("curPage", peopleRequestDto.getCurPage());
            uriBuilder.setParameter("itemPerPage", peopleRequestDto.getItemPerPage());
            uriBuilder.setParameter("peopleName", peopleRequestDto.getPeopleName());
            uriBuilder.setParameter("filmographyName", peopleRequestDto.getFilmographyName());

            URI uri = uriBuilder.build();

            return String.valueOf(uri);

        } catch (URISyntaxException e) {

            e.printStackTrace();
            throw new CustomBatchException("URI 조합 중 에러가 발생했습니다.");
        }
    }

}
