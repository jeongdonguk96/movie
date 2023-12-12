package io.spring.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "TB_ACTOR_TEMP")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class ActorTemp extends People {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "actortemp_id")
    private Long actorTempId;
    private String peopleCode; // 영화인 코드
    private String peopleName; // 영화인 이름

    public ActorTemp(String peopleCode, String peopleName) {
        this.peopleCode = peopleCode;
        this.peopleName = peopleName;
    }
}
