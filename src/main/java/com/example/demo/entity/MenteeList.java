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
/* 멘티 목록 */
public class MenteeList {
//    변수명	                내용	                규격	        제약조건
//    id	                테이블 PK	        BIGINT	    PK
//    mentee_id	            멘티 ID	            BIGINT	    FK
//    mentoring_posts_id	신청 멘토링 글 ID	    BIGINT	    FK
//    start_date	        멘토링 시작 날짜	    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    end_date	            멘토링 종료 날짜	    DATETIME
}
