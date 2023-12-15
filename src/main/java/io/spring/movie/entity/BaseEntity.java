package io.spring.movie.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @CreatedDate
    private LocalDateTime createDate;       // 데이터 생성일

    @LastModifiedDate
    private LocalDateTime lastModifiedDate; // 데이터 최종 수정일
}
