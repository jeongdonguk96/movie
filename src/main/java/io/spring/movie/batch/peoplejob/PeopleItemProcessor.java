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
            if (item.getPeopleCode() == null) {
                Long randomPeopleCode = generateRandomId();
                item.setPeopleCode(randomPeopleCode);
            }
            return new Director(item);
        }
    }

    private Long generateRandomId() {
        long range = 9999999L - 1000000L + 1L;
        long randomPart = (long) (Math.random() * range) + 1000000L;
        return Long.parseLong("9" + randomPart);
    }
}
