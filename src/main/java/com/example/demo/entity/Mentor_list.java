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
/* 멘토 목록 */
public class Mentor_list {
//    변수명	        내용	                규격	        제약조건
//    id	        테이블 PK	        BIGINT	    PK
//    user_id	    사용자 ID	        BIGINT	    FK
//    verified_at	멘토 인증 완료 날짜	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
}
