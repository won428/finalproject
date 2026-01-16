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
/* 강의 헤더 */
public class Courses {
//    변수명	        내용	        규격	        제약조건
//    id	        테이블 PK	BIGINT	    PK
//    course_name	강의명	    VARCHAR	    NOT NULL
//    user_id	    사용자 ID	BIGINT	    FK
//    status	    상태	        ENUM	    NOT NULL
//    price	        가격	        BIGINT	    NOT NULL
//    language_code	언어 분류	ENUM	    NOT NULL
//    created_at	등록 날짜	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
}
