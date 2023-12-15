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
public class Director extends BaseEntity {
    @Id @Column(name = "director_people_code")
    private Long peopleCode;                                     // 영화인 코드
    private String peopleName;                                   // 영화인 이름
    private String sex;                                          // 성별

    @JoinColumn(name = "movie_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;                                         // 영화

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Filmography> filmographies = new ArrayList<>(); // 필모그래피
}
