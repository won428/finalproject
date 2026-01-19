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
/* 손익관리용 자금 흐름 관리 대장 */
public class FinancialLedger {
//    변수명	            내용	                    규격	            제약조건
//    id	            고유 ID	                BIGINT	        PK, AUTO_INCREMENT
//    settlement_id 	정산내역(참조)	        BIGINT	        FK, NULL
//    ad_campaign_id 	광고/마케팅 내역(참조)	    BIGINT	        FK, NULL
//    transaction_type	DEPOSIT(입금/수익),       VARCHAR(10)	NOT NULL
//                      WITHDRAWAL(출금/비용)

//    amount	        금액	                    DECIMAL(15,2)	NOT NULL
//    transaction_date		                    DATETIME	    DEFAULT CURRENT_TIMESTAMP
//    note	            비고                     VARCHAR(500)	NULL
//                      (예: 1차 중도금 입금,
//                       1월 구글 광고비 결제)


/*  [comments]
1.  표준 SQL 문법 상 FK는 하나의 테이블을 정확히 지정해야 하는데, ref_type과 ref_id는  settlements 테이블의 ID가 되기도 하고,
    ad_campaigns 테이블의 ID가 되기도 하기 때문에 settlement_id와 ad_campaign_id는 NULL을 허용함. FK 참조는 각 컬럼당 따로 걸기.
*/




/*  [DDL]
CREATE TABLE financial_ledger (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- 참조 컬럼을 분리하고 각각 FK를 건다 (둘 중 하나만 값이 들어감)
    settlement_id BIGINT NULL,
    ad_campaign_id BIGINT NULL,

    transaction_type VARCHAR(10) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,

    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    note VARCHAR(500) NULL -- 비고 (예: 1차 중도금 입금, 1월 구글 광고비 결제)

    -- FK 제약조건 설정 (데이터 무결성 보장됨)
    FOREIGN KEY (settlement_id) REFERENCES settlements(id),
    FOREIGN KEY (ad_campaign_id) REFERENCES ad_campaigns(id)
);
*/
}
