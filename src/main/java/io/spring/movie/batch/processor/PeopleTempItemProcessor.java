package io.spring.movie.batch.processor;

import io.spring.movie.dto.PeopleListResponseDto.PeopleListResult.PeopleDto;
import io.spring.movie.entity.ActorTemp;
import io.spring.movie.entity.DirectorTemp;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeopleTempItemProcessor implements ItemProcessor<List<PeopleDto>, Map<String, Object>> {

    @Override
    public Map<String, Object> process(List<PeopleDto> item) {
        Map<String, Object> peopleListMap = new HashMap<>();

        for (PeopleDto peopleDto : item) {
            if ("배우".equals(peopleDto.getRepresentRoleName())) {
                peopleListMap.put("actor", new ActorTemp(peopleDto.getPeopleCode(), peopleDto.getPeopleName()));
            }
            if ("감독".equals(peopleDto.getRepresentRoleName())) {
                peopleListMap.put("director", new DirectorTemp(peopleDto.getPeopleCode(), peopleDto.getPeopleName()));
            }
        }
        System.out.println("map에 담긴 수 = " + peopleListMap.size());

        return peopleListMap;
    }
}
