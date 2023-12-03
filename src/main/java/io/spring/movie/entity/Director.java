package io.spring.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TB_DIRECTOR")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class Director {
    @Id @Column(name = "director_people_code")
    private Long peopleCode;                                     // 영화인 코드
    private String peopleName;                                   // 영화인 이름
    private String sex;                                          // 성별

    @OneToMany
    @JoinColumn(name = "filmography_Id")
    private List<Filmography> filmographies = new ArrayList<>(); // 필모그래피
}
