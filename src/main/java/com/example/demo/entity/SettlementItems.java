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
/* 정산 상세 */
public class SettlementItems {
//    변수명	            내용	                    규격	            제약조건
//    id	            고유ID	                BIGINT	        PK, AUTO_INCREMENT
//    settlement_id	    정산내역 ID	            BIGINT	        FK, NOT NULL
//    payment_id	    유저가 실제 결제한 내역	    BIGINT	        FK, NOT NULL
//    course_amount	    해당 건 결제금액	        DECIMAL(15,2)	NOT NULL


/*  [DDL]
CREATE TABLE settlement_items (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	settlement_id BIGINT NOT NULL,
	payment_id BIGINT NOT NULL,   -- 실제 유저가 결제한 내역 ID
	course_amount DECIMAL(15, 2) NOT NULL, -- 해당 건의 결제 금액

	FOREIGN KEY (settlement_id) REFERENCES settlements(id)
);
 */
}
