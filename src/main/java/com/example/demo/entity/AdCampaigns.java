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
/* 광고/마케팅 관리 대장 */
public class AdCampaigns {
//    변수명	            내용	                            규격	            제약조건
//    id	            고유 ID	                        BIGINT	        PK, AUTO_INCREMENT
//    type	            INCOMING(사이트 내 광고 수주),     VARCHAR(20)	    NOT NULL
//                      OUTGOING(외부 마케팅 집행)

//    client_name	    거래처 업체명	                    VARCHAR(100)	NOT NULL
//    title	            캠페인 제목	                    VARCHAR(200)	NOT NULL
//    start_date	    게시 시작일	                    DATE	        NOT NULL
//    end_date	        게시 종료일	                    DATE	        NOT NULL
//    constract_amount	계약 금액	                    DECIMAL(15,2)	NOT NULL
//    status	        PLANNED(광고 기획중),             VARCHAR(20)	    DEFAULT 'ACTIVE’
//                      ACTIVE(게시중),
//                      ENDED(게시종료),
//                      CANCELED(계약취소)

//    creadted_at	    데이터 생성일	                    DATETIME	    DEFAULT CURRENT_TIMESTAMP


/*  [DDL]
CREATE TABLE ad_campaigns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL,         -- INCOMING(사이트 내 광고 수주), OUTGOING(외부 마케팅 집행)

    -- 거래처 정보
    client_name VARCHAR(100) NOT NULL, -- 광고주(Incoming) 또는 매체사(Outgoing, 예: Google, Meta)

    -- 광고 내용
    title VARCHAR(200) NOT NULL,       -- 캠페인 명
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,

    -- 금액
    contract_amount DECIMAL(15, 2) NOT NULL, -- 계약 금액
    status VARCHAR(20) DEFAULT 'ACTIVE',     -- PLANNED, ACTIVE, ENDED, CANCELED

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
 */
}
