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
/* 전문가 신청 내역 */
public class ExpertApplications {
//    변수명	            내용	        규격	        제약조건
//    id	            테이블 ID	BIGINT	    PK
//    user_id	        신청자 ID	BIGINT	    FK NOT NULL
//    status	        상태	        ENUM	    NOT NULL DEFAULT PENDING
//    submitted_at	    제출 시간	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    reviewed_at	    처리 시간	DATETIME
//    reviewer_admin_id	처리자 ID	BIGINT	    FK NOT NULL
//    reject_reason	    반려 사유	VARCHAR
}
