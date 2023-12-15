package io.spring.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "TB_CAST")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class Cast extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "cast_id")
    private Long castId;              // 캐스트 번호
    private String peopleName;        // 배우 이름
    private String cast;              // 역할 이름
}
