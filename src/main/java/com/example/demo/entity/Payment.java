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
/* 결제완료 */
public class Payment {
//    변수명	                내용	                            규격	        제약조건
//    id	                테이블 PK	                    BIGINT	    PK
//    order_id	            주문 내역 FK	                    BIGINT	    FK
//    provider	            결제 수단(toss, kcp, inicis…)	ENUM	    NOT NULL
//    amount	            금액	                            BIGINT	    NOT NULL
//    status	            성공, 실패, 대기	                ENUM	    NOT NULL
//    provider_payment_id	pg 거래키	                    VARCHAR	    UNIQUE
//    requested_at	        요청 시간	                    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    completed_at	        성공 시간	                    DATETIME
//    requested_at	        실패 시간	                    DATETIME
}
