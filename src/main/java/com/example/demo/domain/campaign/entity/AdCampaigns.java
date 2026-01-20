package com.example.demo.domain.campaign.entity;

import com.example.demo.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ad_campaigns")
/* 광고/마케팅 관리 대장 */
public class AdCampaigns extends BasePkEntity {

    //    변수명	            내용	                            규격	            제약조건
    //    id	            고유 ID	                        BIGINT	        PK, AUTO_INCREMENT
    //    type	            INCOMING(사이트 내 광고 수주),     VARCHAR(20)	    NOT NULL
    //                      OUTGOING(외부 마케팅 집행)
    //
    //    client_name	    거래처 업체명	                    VARCHAR(100)	NOT NULL
    //    title	            캠페인 제목	                    VARCHAR(200)	NOT NULL
    //    start_date	    게시 시작일	                    DATE	        NOT NULL
    //    end_date	        게시 종료일	                    DATE	        NOT NULL
    //    contract_amount	계약 금액	                    DECIMAL(15,2)	NOT NULL
    //    status	        PLANNED(광고 기획중),             VARCHAR(20)	    DEFAULT 'ACTIVE'
    //                      ACTIVE(게시중),
    //                      ENDED(게시종료),
    //                      CANCELED(계약취소)
    //
    //    created_at	    데이터 생성일	                    DATETIME	    DEFAULT CURRENT_TIMESTAMP


    // INCOMING / OUTGOING
    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @Column(name = "client_name", nullable = false, length = 100)
    private String clientName;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "contract_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal contractAmount;

    // PLANNED / ACTIVE / ENDED / CANCELED
    @Column(name = "status", length = 20, nullable = false)
    @ColumnDefault("'ACTIVE'")
    private String status = "ACTIVE";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
