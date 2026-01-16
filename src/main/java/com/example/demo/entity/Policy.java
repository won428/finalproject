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
/* 약관 본문, 버전, 시행 관리 */
public class Policy {
//    변수명	        내용                                  	규격	            제약조건
//    id	        테이블의 PK	                            BIGINT	        PK
//    type	        약관 종류	                            VARCHAR(50) 	UNIQUE, NOT NULL
//    version	    개정 버전	                            LONGTEXT	    UNIQUE, NOT NULL
//    content	    약관본문	                                DATETIME	    NOT NULL
//    published_at	발행 시점(시행일 아님)	                    DATETIME	    NOT NULL
//    effective_at	효력을 갖는 날짜(시행일)	                DATETIME	    NULL
//    created_at	해당 행이 언제 생겼는지	                                    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	언제 개정됐는지(덮어씌우기 X, 날짜만 변경    	DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
//    status	    초안/발행/ 폐기 등 상태                     VARCHAR(20)	NOT NULL DEFAULT 'DRAFT’
//                  DRAFT / PUBLISHED / RETIRED


/*  [comments]
유저가 동의 / 거부/ 미동의할 약관들의 버전과 내용, 개정일, 시행일 등을 관리할 테이블
 */

}
