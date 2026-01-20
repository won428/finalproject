package com.example.demo.domain.course.entity;

import com.example.demo.base.BaseTimeEntity;
import com.example.demo.domain.user.entity.User;
import com.example.demo.enums.LanguageCode;
import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="user")
@Entity
@Table(name = "courses")
/* 강의 헤더 */
public class Course extends BaseTimeEntity {
//    변수명	        내용	        규격	        제약조건
//    id	        테이블 PK	BIGINT	    PK
//    course_name	강의명	    VARCHAR	    NOT NULL
//    user_id	    사용자 ID	BIGINT	    FK, NOT NULL
//    status	    상태	        ENUM	    NOT NULL
//    price	        가격	        BIGINT	    NOT NULL
//    language_code	언어 분류	ENUM	    NOT NULL
//    created_at	등록 날짜	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at    수정 날짜     DATETIME

    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

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
            foreignKey = @ForeignKey(name = "fk_course_user")
    )
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.PENDING;

    @Column(name = "price", nullable = false)
    private Long price; // v2 만들 때 DDL에서 음수 막아야합니다.

    @Enumerated(EnumType.STRING)
    @Column(name = "language_code", nullable = false, length = 3)
    private LanguageCode languageCode;

}
