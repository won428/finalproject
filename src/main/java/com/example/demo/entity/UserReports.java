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
/* 신고 내역 */
public class UserReports {
//    변수명	            내용	                        규격	            제약조건
//    id	            테이블 PK	                BIGINT	        PK, AUTO_INCREMENT
//    reporter_id	    신고자 ID	                BIGINT	        FK, NOT NULL
//    post_id	        게시글 ID	                BIGINT	        NULL
//    target_id	        신고 당한 사람 ID	            BIGINT	        NULL
//    reason	        사유(라디오 버튼 형태)	        ENUM	        NOT NULL
//    description	    설명(신고하는 사유 부가설명)	VARCHAR(500)	NOT NULL
//    created_at	    신고 날짜	                DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP
}
