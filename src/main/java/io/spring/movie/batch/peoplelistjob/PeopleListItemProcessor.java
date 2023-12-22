package io.spring.movie.batch.peoplelistjob;

import io.spring.movie.batch.dto.PeopleListResponseDto.PeopleListResultDto.PeopleDto;
import io.spring.movie.batch.temp.PeopleTemp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class PeopleListItemProcessor implements ItemProcessor<List<PeopleDto>, List<PeopleTemp>> {

    @Override
    public List<PeopleTemp> process(List<PeopleDto> item) {
        if (Objects.isNull(item)) {
            return null;
        }

        List<PeopleTemp> peopleTempList = new ArrayList<>();
        item.forEach(peopleDto -> {
            if ("배우".equals(peopleDto.getRepresentRoleName()) || "감독".equals(peopleDto.getRepresentRoleName())) {
                peopleTempList.add(new PeopleTemp(peopleDto.getPeopleCode(), peopleDto.getPeopleName()));
            }
        });

        log.info("배우/감독 조회 수 = " + peopleTempList.size());
        return peopleTempList;
    }
}