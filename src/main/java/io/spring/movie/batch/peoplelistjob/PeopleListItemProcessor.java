package io.spring.movie.batch.peoplelistjob;

import io.spring.movie.batch.dto.PeopleListResponseDto.PeopleListResultDto.PeopleDto;
import io.spring.movie.entity.ActorTemp;
import io.spring.movie.entity.DirectorTemp;
import io.spring.movie.entity.People;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class PeopleListItemProcessor implements ItemProcessor<List<PeopleDto>, List<People>> {

    @Override
    public List<People> process(List<PeopleDto> item) {
        if (Objects.isNull(item)) {
            return null;
        }

        List<People> peopleList = new ArrayList<>();

        item.forEach(peopleDto -> {
            if ("배우".equals(peopleDto.getRepresentRoleName())) {
                peopleList.add(new ActorTemp(peopleDto.getPeopleCode(), peopleDto.getPeopleName()));
            }
            if ("감독".equals(peopleDto.getRepresentRoleName())) {
                peopleList.add(new DirectorTemp(peopleDto.getPeopleCode(), peopleDto.getPeopleName()));
            }
        });
        log.info("배우/감독 조회 수 = " + peopleList.size());

//        for (PeopleDto peopleDto : item) {
//            if ("배우".equals(peopleDto.getRepresentRoleName())) {
//                peopleList.add(new ActorTemp(peopleDto.getPeopleCode(), peopleDto.getPeopleName()));
//            }
//            if ("감독".equals(peopleDto.getRepresentRoleName())) {
//                peopleList.add(new DirectorTemp(peopleDto.getPeopleCode(), peopleDto.getPeopleName()));
//            }
//        }

        return peopleList;
    }
}
