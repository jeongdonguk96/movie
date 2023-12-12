package io.spring.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "TB_DIRECTOR_TEMP")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class DirectorTemp extends People {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "directortemp_id")
    private Long directorTempId;
    private String peopleCode; // 영화인 코드
    private String peopleName; // 영화인 이름

    public DirectorTemp(String peopleCode, String peopleName) {
        this.peopleCode = peopleCode;
        this.peopleName = peopleName;
    }
}
