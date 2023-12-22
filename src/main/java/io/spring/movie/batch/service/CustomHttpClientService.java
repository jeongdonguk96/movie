package io.spring.movie.batch.service;

import io.spring.movie.batch.dto.PeopleListRequestDto;
import io.spring.movie.batch.dto.PeopleRequestDto;
import io.spring.movie.exception.CustomBatchException;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class CustomHttpClientService {

    public static String buildUrl(PeopleListRequestDto peopleListRequestDto) {

        try {
            URIBuilder uriBuilder = new URIBuilder(peopleListRequestDto.getUrl());
            uriBuilder.setParameter("key", peopleListRequestDto.getKey());
            uriBuilder.setParameter("curPage", peopleListRequestDto.getCurPage());
            uriBuilder.setParameter("itemPerPage", peopleListRequestDto.getItemPerPage());
            uriBuilder.setParameter("peopleName", peopleListRequestDto.getPeopleName());
            uriBuilder.setParameter("filmographyName", peopleListRequestDto.getFilmographyName());

            URI uri = uriBuilder.build();

            return String.valueOf(uri);

        } catch (URISyntaxException e) {

            e.printStackTrace();
            throw new CustomBatchException("URI 조합 중 에러가 발생했습니다.");
        }
    }

    public static String buildUrl(PeopleRequestDto peopleRequestDto) {

        try {
            URIBuilder uriBuilder = new URIBuilder(peopleRequestDto.getUrl());
            uriBuilder.setParameter("key", peopleRequestDto.getKey());
            uriBuilder.setParameter("peopleCd", peopleRequestDto.getPeopleCode());

            URI uri = uriBuilder.build();

            return String.valueOf(uri);

        } catch (URISyntaxException e) {

            e.printStackTrace();
            throw new CustomBatchException("URI 조합 중 에러가 발생했습니다.");
        }
    }

}
