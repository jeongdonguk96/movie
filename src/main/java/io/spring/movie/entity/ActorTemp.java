package io.spring.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActorTemp extends People {
    private String peopleCode; // 영화인 코드
    private String peopleName; // 영화인 이름
}
