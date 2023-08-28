/*package com.example.exam_board.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass //양방향 연관관계일때 선언
@Data
public class BaseEntity {

    @CreatedDate
    LocalDateTime createdAt;
    @LastModifiedDate
    LocalDateTime modifiedAt;
}
*/