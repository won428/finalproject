package com.example.demo.domain.course.entity;

import com.example.demo.base.entity.BaseRequestTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="course")
@Entity
@Table(name = "course_requests")
/* 강의 등록 신청내역 */
public class CourseRequest extends BaseRequestTimeEntity {
//    변수명	        내용	        규격	        제약조건
//    id	        테이블 PK	BIGINT	    PK
//    course_id	    강의 FK	    BIGINT	    FK
//    price	        가격	        BIGINT	    NOT NULL
//    description	설명	        VARCHAR 	NOT NULL
//    status        상태         ENUM        NOT NULL DEFAULT PENDING
//    submitted_at	신청시간	    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    reviewed_at	처리시간	    DATETIME

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
            foreignKey = @ForeignKey(name = "fk_course_request_course")
    )
    private Course course;


    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;



}
