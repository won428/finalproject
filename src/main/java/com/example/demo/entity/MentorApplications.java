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
/* 멘토 신청 내역 */
public class MentorApplications {
//    변수명	            내용	        규격	        제약조건
//    id	            테이블 PK	BIGINT	    PK
//    user_id	        신청자 ID	BIGINT	    FK NOT NULL
//    status	        상태	        ENUM	    NOT NULL DEFAULT PENDING
//    applied_at	    신청 일자	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    processed_at	    처리 일자	DATETIME
//    processed_by	    처리자 ID	BIGINT	    FK NOT NULL
//    reject_reason	    반려사유	    VARCHAR
}
