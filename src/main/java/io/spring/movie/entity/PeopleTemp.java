package io.spring.movie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "TB_PEOPLE_TEMP")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class PeopleTemp {
    @Id @Column(name = "people_temp_code")
    private Long peopleCode;                                     // 영화인 코드
    private String peopleName;                                   // 영화인 이름
}
