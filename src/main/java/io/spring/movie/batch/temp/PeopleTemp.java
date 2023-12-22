package io.spring.movie.batch.temp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PeopleTemp {
    private String peopleCode; // 영화인 코드
    private String peopleName; // 영화인 이름
}
