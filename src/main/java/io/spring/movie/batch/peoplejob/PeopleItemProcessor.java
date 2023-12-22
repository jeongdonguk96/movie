package io.spring.movie.batch.peoplejob;

import io.spring.movie.batch.dto.PeopleResponseDto.PeopleInfoResultDto.PeopleInfoDto;
import io.spring.movie.entity.Actor;
import io.spring.movie.entity.Director;
import org.springframework.batch.item.ItemProcessor;

public class PeopleItemProcessor implements ItemProcessor<PeopleInfoDto, Object> {

    @Override
    public Object process(PeopleInfoDto item) {
        if ("배우".equals(item.getRoleName())) {
            System.out.println("배우 생성");
            return new Actor(item);
        } else {
            System.out.println("감독 생성");
            return new Director(item);
        }
    }
}

//public class PeopleItemProcessor implements ItemProcessor<PeopleInfoDto, Map<String, Object>> {
//
//    Map<String, Object> peopleMap = new HashMap<>();
//
//    @Override
//    public Map<String, Object> process(PeopleInfoDto item) {
//        if ("배우".equals(item.getRoleName())) {
//            System.out.println("배우 생성");
//            peopleMap.put("배우", new Actor(item));
//        } else {
//            System.out.println("감독 생성");
//            peopleMap.put("감독", new Director(item));
//        }
//
//        return peopleMap;
//    }
//}
