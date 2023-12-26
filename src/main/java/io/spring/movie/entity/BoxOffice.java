package io.spring.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TB_BOXOFFICE")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class BoxOffice extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "boxoffice_id")
    private Long boxofficeId;                       // 박스오피스 번호
    private String ranking;                         // 순위
    private String rankingIntensity;                // 순위 증감분
    private String rankingOldOrNew;                 // 신규 진입 여부
    private String salesAmount;                     // 해당일 매출액
    private String salesIntensity;                  // 전날 대비 매출액 증감분
    private String salesChange;                     // 전날 대비 매출액 증감 비율
    private String salesAccumulated;                // 누적 매출액
    private String audienceCount;                   // 해당일 관객 수
    private String audienceIntensity;               // 전날 대비 관객 수 증감분
    private String audienceChange;                  // 전날 대비 관객 수 증감 비율
    private String audienceAccumulated;             // 누적 관객 수
    private String screenCount;                     // 해당일 상영 스크린 수
    private String showCount;                       // 해당일 상영 횟수
    private String boxofficeGubun;                  // 일별/주간별 구분

    @OneToMany(mappedBy = "boxOffice", fetch = FetchType.LAZY)
    private List<Movie> movies = new ArrayList<>(); // 영화 정보
}
