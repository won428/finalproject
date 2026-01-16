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
/*  */
public class CourseOrders {
//    변수명	내용	규격	제약조건
//    id	            테이블 PK	    BIGINT	    PK
//    order_code	    주문 번호	    VARCHAR	    UNIQUE, NOT NULL
//    user_id	        구매자 id	    BIGINT	    FK
//    date	            구매 날짜	    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    status	        결제 대기,        ENUM	    NOT NULL
//                      결제 완료,
//                      취소,
//                      부분환불 등등

//    total_amount	    총 금액	        BIGINT	    NOT NULL
//    coupon	        적용 쿠폰 FK	    BIGINT
//    discount_percent	할인율	        BIGINT
//    discount_amount	할인 금액	    BIGINT
//    paid_amount	    결제 성공 금액	BIGINT
//    refunded_amount	환불 금액	    BIGINT
}
