package com.example.demo.domain.settlement.entity;

import com.example.demo.base.entity.BasePkEntity;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = "instructor")
@Entity
@Table(
        name = "settlements",
        indexes = {
                @Index(name = "idx_instructor_period", columnList = "instructor_id, period_start")
        }
)
/* 정산 내역 */
public class Settlement extends BasePkEntity {
    //    변수명	                내용	                규격	                제약조건
//    id	                고유 ID	            BIGINT	            PK, AUTO_INCREMENT
//    instructor_id	        정산받을 강사 ID	    BIGINT	            FK, NOT NULL
//    period_start	        정산 기준 시작일	    DATE	            NOT NULL
//    period_end	        정산 기준 종료일	    DATE	            NOT NULL
//    total_sales_amount	콘텐츠 총 판매액	    DECIMAL(15, 2)	    NOT NULL
//    platform_fee_rate	    적용된 수수료율	    DECIMAL(5,2)	    NOT NULL
//    platform_fee_amount	공제한 플랫폼 수수료	DECIMAL(15,2)	    NOT NULL
//    tax_amount	        원천징수 세금	        DECIMAL(15,2)	    NOT NULL
//    final_payout_amount	실제 입금액	        DECIMAL(15,2)	    NOT NULL
//    status	            PENDING(대기),       VARCHAR(20)	        DEFAULT 'PENDING’
//                          APPROVED(승인),
//                          PAID(지급완료),
//                          REJECTED(보류)

//    paid_at	            실제 지급일	        DATETIME	        NULL
//    created_at	        행 생성일	        DATETIME	        DEFAULT CURRENT_TIMESTAMP


    // FK 컬럼(쓰기용)
    @Column(name = "instructor_id", nullable = false)
    private Long instructorId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "instructor_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_settlement_instructor")
    )
    private User instructor;

    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;

    @Column(name = "total_sales_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalSalesAmount;

    @Column(name = "platform_fee_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal platformFeeRate;

    @Column(name = "platform_fee_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal platformFeeAmount;

    @Column(name = "tax_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "final_payout_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal finalPayoutAmount;

    @Column(name = "status", length = 20, nullable = false)
    @ColumnDefault("'PENDING'")
    private String status = "PENDING";

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    /*  [DDL]
CREATE TABLE settlements (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	instructor_id BIGINT NOT NULL,          -- 정산 받을 강사 ID

	-- 정산 기준 기간 (예: 2024-01-01 ~ 2024-01-31)
	period_start DATE NOT NULL,
	period_end DATE NOT NULL,

	-- 금액 상세 (중요: 모든 계산 근거를 남겨야 함)
	total_sales_amount DECIMAL(15, 2) NOT NULL, -- 기간 내 총 판매액 (VAT 포함)
	platform_fee_rate DECIMAL(5, 2) NOT NULL,   -- 적용된 수수료율 (예: 20.00%)
	platform_fee_amount DECIMAL(15, 2) NOT NULL,-- 공제한 플랫폼 수수료 (VAT 별도)
	tax_amount DECIMAL(15, 2) NOT NULL,         -- 원천징수 세금 (3.3% 등)

	final_payout_amount DECIMAL(15, 2) NOT NULL,-- 실제 입금액 (판매액 - 수수료 - 세금)

	-- 상태 관리
	status VARCHAR(20) DEFAULT 'PENDING',       -- PENDING(대기), APPROVED(승인), PAID(지급완료), REJECTED(보류)
	paid_at DATETIME NULL,                      -- 실제 지급 일시

	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

	INDEX idx_instructor_period (instructor_id, period_start)

);
*/
}
