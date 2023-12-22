package io.spring.movie.entity;

import io.spring.movie.batch.dto.PeopleResponseDto.PeopleInfoResultDto.PeopleInfoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TB_ACTOR")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class Actor extends BaseEntity {
    @Id @Column(name = "actor_people_code")
    private Long peopleCode;                                     // 영화인 코드
    private String peopleName;                                   // 영화인 이름
    private String sex;                                          // 성별

    @JoinColumn(name = "movie_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;                                         // 영화

    @OneToMany(mappedBy = "actor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Filmography> filmographies = new ArrayList<>(); // 필모그래피

    public Actor(PeopleInfoDto peopleInfoDto) {
        this.peopleCode = peopleInfoDto.getPeopleCode();
        this.peopleName = peopleInfoDto.getPeopleName();
        this.sex = peopleInfoDto.getSex();
    }
}
