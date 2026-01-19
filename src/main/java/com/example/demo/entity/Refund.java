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
/* 환불, 취소 내역 */
public class Refund {
//    변수명	                내용                      	규격	            제약조건
//    id	                테이블 PK	                BIGINT	        PK
//    order_id	            주문 fk	                    BIGINT	        FK
//    payment_id	        결제정보 fk	                BIGINT	        FK
//    status	            상태(대기, 승인, 성공, 취소)	ENUM
//    reason_code	        환불 사유(간략히)	            ENUM	        NOT NULL
//    reason_detail	        환불 사유(자세히)	            VARCHAR
//    provider_refund_id	PG 식별자	                VARCHAR	        UNIQUE
//    amount	            요청 금액	                BIGINT	        NOT NULL
//    requested_at	        요청 시간	                DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP
//    completed_at	        성공 시간	                DATETIME
//    failed_at	            실패 시간	                DATETIME
//    fail_code	            실패 사유(간략히)	            ENUM
//    fail_detail	        실패 사유(자세히)	            VARCHAR
}
