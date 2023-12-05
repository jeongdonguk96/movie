package io.spring.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "TB_FILMOGRAPHY")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class Filmography {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "filmography_id")
    private Long filmographyId;     // 필모그래피 번호
    private Long movieCode;         // 영화 코드
    private String movieName;       // 영화 제목
    private String moviePartName;   // 영화인 참여 역할
    private String companyPartName; // 영화사 참여 역할

    @JoinColumn(name = "actor_people_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Actor actor;            // 배우

    @JoinColumn(name = "director_people_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Director director;      // 감독

    @JoinColumn(name = "company_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;        // 영화사

    @OneToOne
    private Cast cast;              // 배우 역할
}
