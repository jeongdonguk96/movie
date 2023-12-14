package io.spring.movie.batch.peoplelistjob;

import io.spring.movie.batch.dto.PeopleListResponseDto.PeopleListResultDto.PeopleDto;
import io.spring.movie.entity.ActorTemp;
import io.spring.movie.entity.DirectorTemp;
import io.spring.movie.entity.People;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class PeopleListItemProcessor implements ItemProcessor<List<PeopleDto>, List<People>> {

    @Override
    public List<People> process(List<PeopleDto> item) {
        if (item == null) {
            return null;
        }

        List<People> peopleList = new ArrayList<>();

        for (PeopleDto peopleDto : item) {
            if ("배우".equals(peopleDto.getRepresentRoleName())) {
                peopleList.add(new ActorTemp(peopleDto.getPeopleCode(), peopleDto.getPeopleName()));
            }
            if ("감독".equals(peopleDto.getRepresentRoleName())) {
                peopleList.add(new DirectorTemp(peopleDto.getPeopleCode(), peopleDto.getPeopleName()));
            }
        }
        System.out.println("배우/감독 조회 수 = " + peopleList.size());

        return peopleList;
    }
}
