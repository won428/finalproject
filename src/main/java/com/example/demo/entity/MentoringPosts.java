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
/* 멘토링 글 목록 */
public class MentoringPosts {
//    변수명	                    내용	            규격	        제약조건
//    id	                    테이블 PK	    BIGINT	    PK
//    tags	                    태그	            BIGINT	    FK
//    mentor_id	                멘토 ID	        BIGINT  	FK NOT NULL
//    program_period	        멘토링 진행 기간	DATETIME	NOT NULL
//    session_count	            멘토링 진행 횟수	BIGINT	    NOT NULL
//    title	                    제목	            VARCHAR	    NOT NULL
//    content	                내용	            VARCHAR	    NOT NULL
//    capacity	                모집 정원	    BIGINT	    NOT NULL DEFAULT 1
//    recruitment_start_date	모집 기간	    DATETIME	NOT NULL
//    recruitment_end_date	    모집 종료 기간	DATETIME	NOT NULL
//    created_at	            생성 날짜	    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    tech_stack	            기술 스택	    ENUM	    NOT NULL
}
