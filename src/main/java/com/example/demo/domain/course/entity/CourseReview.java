package com.example.demo.domain.course.entity;

import com.example.demo.base.entity.BaseTimeEntity;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"user", "course"})
@Entity
@Table(
        name = "course_reviews",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_reviews_user_course",
                        columnNames = {"user_id", "course_id"}
                )
        },
        indexes = {
                @Index(name = "ix_course_reviews_course", columnList = "course_id"),
                @Index(name = "ix_course_reviews_user", columnList = "user_id")
        }
)
/* 수강평 */
public class CourseReview extends BaseTimeEntity {
//    변수명        내용        규격           제약조건
//    id          테이블 PK    BIGINT         PK
//    user_id     작성자 ID    BIGINT         FK, NOT NULL
//    course_id   강의 ID      BIGINT         FK, NOT NULL
//    star        별점         TINYINT        NOT NULL (1~5)
//    content     수강평       VARCHAR(200)
//    created_at  등록 날짜    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at  수정 날짜    DATETIME

    // FK 컬럼(쓰기용)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_course_reviews_user")
    )
    private User user;

    // FK 컬럼(쓰기용)
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "course_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_course_reviews_course")
    )
    private Course course;

    @Column(name = "star", nullable = false)
    @Min(1)
    @Max(5)
    private Byte star; // 1~5 (DB CHECK 또는 서비스 로직으로 검증)

    @Column(name = "content", length = 200)
    private String content;
}
