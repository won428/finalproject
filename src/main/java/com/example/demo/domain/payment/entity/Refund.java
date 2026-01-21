package com.example.demo.domain.payment.entity;

import com.example.demo.base.entity.BasePkEntity;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.base.enums.FailCode;
import com.example.demo.base.enums.ReasonCode;
import com.example.demo.base.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"order", "payment"})
@Entity
@Table(name = "refund")
/* 환불, 취소 내역 */
public class Refund extends BasePkEntity {
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
            foreignKey = @ForeignKey(name = "fk_refund_order")
    )
    private Order order;

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
            foreignKey = @ForeignKey(name = "fk_refund_payment")
    )
    private Payment payment;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "reason_code", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ReasonCode reasonCode;

    @Column(name = "reason_detail", length = 500)
    private String reasonDetail;

    @Column(name = "provider_refund_id", length = 100, unique = true)
    private String providerRefundId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @CreationTimestamp
    @Column(name = "requested_at", nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "failed_at")
    private LocalDateTime failedAt;

    @Column(name = "fail_code", length = 30)
    @Enumerated(EnumType.STRING)
    private FailCode failCode;

    @Column(name = "fail_detail", length = 500)
    private String failDetail;
}
