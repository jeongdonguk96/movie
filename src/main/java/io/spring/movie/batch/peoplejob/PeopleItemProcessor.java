package io.spring.movie.batch.peoplejob;

import io.spring.movie.batch.dto.PeopleResponseDto.PeopleInfoResultDto.PeopleInfoDto;
import io.spring.movie.entity.Actor;
import io.spring.movie.entity.Director;
import org.springframework.batch.item.ItemProcessor;

public class PeopleItemProcessor implements ItemProcessor<PeopleInfoDto, Object> {

    @Override
    public Object process(PeopleInfoDto item) {
        if ("배우".equals(item.getRoleName())) {
            return new Actor(item);
        } else {
            return new Director(item);
        }
    }
}
