package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "")
/* 강의 등록 신청내역 */
public class CourseRequests {
//    변수명	        내용	        규격	        제약조건
//    id	        테이블 PK	BIGINT	    PK
//    course_id	    강의 FK	    BIGINT	    FK
//    price	        가격	        BIGINT	    NOT NULL
//    description	설명	        VARCHAR 	NOT NULL
//    status        상태         ENUM        NOT NULL DEFAULT PENDING
//    submitted_at	신청시간	    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    reviewed_at	처리시간	    DATETIME
}
