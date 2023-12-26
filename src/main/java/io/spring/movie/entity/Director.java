package io.spring.movie.entity;

import io.spring.movie.batch.dto.PeopleResponseDto.PeopleInfoResultDto.PeopleInfoDto;
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
public class Director extends BaseEntity {
    @Id @Column(name = "director_people_code")
    private Long peopleCode;                                     // 영화인 코드
    private String peopleName;                                   // 영화인 이름
    private String sex;                                          // 성별

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    private List<PeopleMovie> peopleMovies = new ArrayList<>();  // 영화 (@ManyToMany 대체)

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    private List<Filmography> filmographies = new ArrayList<>(); // 필모그래피

    public Director(PeopleInfoDto peopleInfoDto) {
        this.peopleCode = peopleInfoDto.getPeopleCode();
        this.peopleName = peopleInfoDto.getPeopleName();
        this.sex = peopleInfoDto.getSex();
    }
}
