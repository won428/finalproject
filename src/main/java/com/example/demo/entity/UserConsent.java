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
/* 개인정보 수집 동의/거부 */
public class UserConsent {
//    변수명	내용	규격	제약조건
//    id		                                                BIGINT	        PK
//    user_id	    유저 ID	                                    BIGINT	        FK
//    policy_id	    약관 버전, 본문 관리	                        BIGINT	        UNIQUE, FK
//    consent_type	약관 타입(개인정보, sms 마케팅정보 수신 동의)	    VARCHAR(50)	    UNIQUE, NOT NULL
//    status	    상태                                         TINYINT(1)	    NOT NULL
//                  동의 = 1 / 거부,미동의 = 0
//    given_at	    동의 시작일	                                DATETIME	    NULL
//    revoked_at	약관 철회일	                                DATETIME	    NULL
//    created_at	해당 행이 처음 만들어진 시각	                DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	해당 행이 마지막으로 수정된 시각	                DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


/*  [comments]
유저의 약관 동의 이력 관리용 테이블
policy_id를 참조해 약관(policy table)의 본문, 버전, 시행일 등을 관리함
 */
}
