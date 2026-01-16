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
/* 로컬 로그인 테이블 */
public class UserLocalCredentials {
//    변수명	내용	규격	제약조건
//    id		                                            BIGINT	        PK
//    user_id	            유저 닉네임	                    VARCHAR(50)	    FK
//    email	                이메일	                        VARCHAR(255)	UNIQUE
//    email_verified_at	    이메일 인증 시각	                DATETIME	    NULL
//    password_hash	        암호화된 비밀번호	                VARCHAR(255)	NULL
//    password_updated_at	비밀번호 변경일자	                DATETIME	    NULL
//    created_at	        행이 생성된 날짜	                DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	        행 수정된 날짜(비밀번호 변경 등)	    DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


/*  [comments]
1. ID(이메일)와 PW로 로그인하는 일반 방식.
2. 이메일 인증 NULL 허용
3. 로컬 로그인 시 PW 암호화 필요.
 */
}
