package com.example.demo.domain.settlement.entity;

import com.example.demo.base.BasePkEntity;
import com.example.demo.domain.payment.entity.Payment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(exclude = {"settlement", "payment"})
@Entity
@Table(name = "settlement_items")
/* 정산 상세 */
public class SettlementItem extends BasePkEntity {
//    변수명	            내용	                    규격	            제약조건
//    id	            고유ID	                BIGINT	        PK, AUTO_INCREMENT
//    settlement_id	    정산내역 ID	            BIGINT	        FK, NOT NULL
//    payment_id	    유저가 실제 결제한 내역	    BIGINT	        FK, NOT NULL
//    course_amount	    해당 건 결제금액	        DECIMAL(15,2)	NOT NULL


    // FK 컬럼(쓰기용)
    @Column(name = "settlement_id", nullable = false)
    private Long settlementId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "settlement_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_settlement_item_settlement")
    )
    private Settlement settlement;

    // FK 컬럼(쓰기용)
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "payment_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_settlement_item_payment")
    )
    private Payment payment;

    @Column(name = "course_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal courseAmount;

    /*  [DDL]
CREATE TABLE settlement_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    settlement_id BIGINT NOT NULL,
    payment_id BIGINT NOT NULL,   -- 실제 유저가 결제한 내역 ID
    course_amount DECIMAL(15, 2) NOT NULL, -- 해당 건의 결제 금액

    CONSTRAINT fk_settlement_item_settlement
        FOREIGN KEY (settlement_id) REFERENCES settlements(id),

    CONSTRAINT fk_settlement_item_payment
        FOREIGN KEY (payment_id) REFERENCES payment(id)
);
 */
}
