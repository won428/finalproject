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
/* 강의 접근 권한 */
public class UserEntitlements {
//    변수명	            내용	            규격	        제약조건
//    id	            테이블 PK	    BIGINT	    PK
//    course_id	        강의 FK	        BIGINT	    FK, NOT NULL, UNIQUE
//    user_id	        유저 FK	        BIGINT	    FK, NOT NULL, UNIQUE
//    status	        상태(승인, 취소)	ENUM	    NOT NULL DEFAULT APPROVED
//    created_at	    생성 날짜	    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP


/*  [comments]
결제 완료가 되면 해당 강의에 대한 접근 권한을 유저에게 부여하고,
환불이나 취소등 절차가 진행되면 해당 권한을 취소하는 방식으로 로직이 짜여진다면 필요한 권한 테이블 입니다.
 */
}
