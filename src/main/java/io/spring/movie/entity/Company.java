package io.spring.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TB_COMPANY")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class Company extends BaseEntity {
    @Id  @Column(name = "company_code")
    private Long companyCode;                                    // 영화사 코드
    private String companyName;                                  // 영화사 이름
    private String companyPartNames;                             // 영화사 분류
    private String ceoNames;                                     // 대표자명

    @JoinColumn(name = "movie_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;                                         // 영화

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Filmography> filmographies = new ArrayList<>(); // 필모그래피
}
