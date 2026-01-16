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
/* 사용자의 상태 관리 */
public class UserStatusHistory {
//    변수명	내용	규격	제약조건
//    id		                                        BIGINT	        PK AUTO_INCREMENT
//    user_id	        상태 변경된 유저의 ID	            BIGINT	        FK, NOT NULL
//    prev_status	    변경 전 상태	                    VARCHAR(20)	    NOT NULL
//    new_status	    변경 후 상태	                    VARCHAR(20)	    NOT NULL
//    changed_by	    누가 상태를 변경했는지(관리자의 ID)	BIGINT	        NULL
//    change_reason	    변경 사유	                    VARCHAR(500)	NULL
//    changed_at	    변경된 시각	                    DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP


/*  [comments]
1. 증거가 필요하다면 첨부파일 테이블 사용.

2. 실시간 채팅상담 시 부재중인 상담사의 세션연결 필터링 + 일반 학습자, 교육자의 계정 삭제(소프트 삭제), 계정 비활성화 관리 시에 사용되는 DB
 */
}
