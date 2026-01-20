package com.example.demo.domain.payment.entity;

import com.example.demo.base.BasePkEntity;
import com.example.demo.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = "order")
@Entity
@Table(name = "payment")
/* 결제완료 */
public class Payment extends BasePkEntity {
//    변수명	                내용	                            규격	        제약조건
//    id	                테이블 PK	                    BIGINT	    PK
//    order_id	            주문 내역 FK	                    BIGINT	    FK
//    provider	            결제 수단(toss, kcp, inicis…)	ENUM	    NOT NULL
//    amount	            금액	                            BIGINT	    NOT NULL
//    status	            성공, 실패, 대기	                ENUM	    NOT NULL
//    provider_payment_id	pg 거래키	                    VARCHAR	    UNIQUE
//    requested_at	        요청 시간	                    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    completed_at	        성공 시간	                    DATETIME
//    failed_at	        실패 시간	                    DATETIME

    // FK 컬럼(쓰기용)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_payment_order")
    )
    private Order order;

    @Column(name = "provider", nullable = false, length = 30)
    private String provider;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "provider_payment_id", length = 100, unique = true)
    private String providerPaymentId;

    @CreationTimestamp
    @Column(name = "requested_at", nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "failed_at")
    private LocalDateTime failedAt;
}
