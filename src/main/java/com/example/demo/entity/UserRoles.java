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
/* 유저-역할 매핑테이블 */
public class UserRoles {
//    변수명	            내용	        규격	            제약조건
//    user_id	        유저 ID	    BIGINT	        PK, FK,  NOT NULL
//    role_id	        역할 ID	    BIGINT	        PK, FK, NOT NULL
//    granted_at	    행 생성일	DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP

/*  [comments]
하나의 계정에 두개 이상 역할(권한)이 들어갈 수 있으므로 교차 테이블 생성.
 */
}
