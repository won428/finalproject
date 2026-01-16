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
/* 강의 커리큘럼 */
public class CoursesCurriculum {
//    변수명	        내용	        규격	        제약조건
//    id	        테이블 PK	BIGINT	    PK
//    course_id 	강의 FK	    BIGINT	    FK
//    status	    상태	        ENUM	    NOT NULL
//    title	        차시 제목	VARCHAR	    NOT NULL
//    description	차시 설명	VARCHAR	    NOT NULL
//    sort_order	차시 순서	BIGINT	    NOT NULL
}
