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
/* 유저 제한 이력(이력 관리) */
public class UserBanHistory {
//    변수명	            내용	            규격	        제약조건
//    id	            테이블 PK	    BIGINT	    PK
//    ban_id	        제한 유저	    BIGINT	    FK
//    ban_reason	    제한 이유	    ENUM	    NOT NULL
//    ban_start_date	제한 시작 날짜	DATETIME
//    ban_end_date	    제한 종료 날짜	DATETIME
}
