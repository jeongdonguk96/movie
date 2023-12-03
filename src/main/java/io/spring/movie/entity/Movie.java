package io.spring.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TB_MOVIE")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class Movie {
    @Id  @Column(name = "movie_code")
    private Long movieCode;                               // 영화 코드
    private String movieName;                             // 영화 제목
    private String genres;                                // 영화 장르
    private String movieType;                             // 영화 유형
    private String productionYear;                        // 제작 연도
    private String nations;                               // 제작 국가
    private String productionStatus;                      // 제작 상태
    private String openDate;                              // 개봉일
    private String showTime;                              // 상영 시간
    private String auditNo;                               // 심의 번호
    private String watchGradeName;                        // 관람 등급

    @OneToMany
    @JoinColumn(name = "people_code")
    private List<Director> directors = new ArrayList<>(); // 영화 감독

    @OneToMany
    @JoinColumn(name = "people_code")
    private List<Actor> actors = new ArrayList<>();       // 출연 배우

    @OneToMany
    @JoinColumn(name = "company_code")
    private List<Company> companies = new ArrayList<>();  // 참여 영화사
}
