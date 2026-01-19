package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = "user")
@Entity
@Table(
        name = "mentors"
)
/* 멘토 목록 */
public class Mentor extends BasePkEntity {
//    변수명	        내용	                규격	        제약조건
//    id	        테이블 PK	        BIGINT	    PK
//    user_id	    사용자 ID	        BIGINT	    FK, NOT NULL
//    verified_at	멘토 인증 완료 날짜	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_mentors_user") )
    private User user;

    @CreationTimestamp
    @Column(name = "verified_at", updatable = false, nullable = false)
    private LocalDateTime verifiedAt;
}
