package io.spring.movie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "TB_DIRECTOR_TEMP")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class DirectorTemp {
    @Id @Column(name = "director_temp_people_code")
    private Long peopleCode;                                     // 영화인 코드
    private String peopleName;                                   // 영화인 이름
}
