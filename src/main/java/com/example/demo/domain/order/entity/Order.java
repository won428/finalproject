package com.example.demo.domain.order.entity;

import com.example.demo.base.entity.BasePkEntity;
import com.example.demo.domain.coupon.entity.Coupon;
import com.example.demo.domain.user.entity.User;
import com.example.demo.base.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"user"})
@Entity
@Table(name = "orders")
@NoArgsConstructor
/* 강의 주문 */
public class Order extends BasePkEntity {

    // id              테이블 PK        BIGINT      PK  (BasePkEntity)
    // order_code       주문 번호        VARCHAR     UNIQUE, NOT NULL
    // user_id          구매자 id        BIGINT      FK
    // ordered_at             구매 날짜        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP
    // status           결제 대기/완료/취소/부분환불 등 ENUM  NOT NULL
    // total_amount     총 금액          BIGINT      NOT NULL
    // coupon           적용 쿠폰 FK     BIGINT
    // discount_percent 할인율           BIGINT
    // discount_amount  할인 금액        BIGINT
    // paid_amount      결제 성공 금액   BIGINT
    // refunded_amount  환불 금액        BIGINT

    @Column(name = "order_code", nullable = false, unique = true, length = 50)
    private String orderCode;

    // FK 컬럼(쓰기용)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_orders_user")
    )
    private User user;


    @CreationTimestamp
    @Column(name = "ordered_at", updatable = false, nullable = false)
    private LocalDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.PENDING;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    // FK 컬럼(쓰기용) - nullable
    @Column(name = "coupon_id")
    private Long couponId;

    // 연관관계(읽기전용) - nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "coupon_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_orders_coupon")
    )
    private Coupon coupon;

    @Column(name = "discount_percent")
    private Long discountPercent;

    @Column(name = "discount_amount")
    private Long discountAmount;

    @Column(name = "paid_amount")
    private Long paidAmount;

    @Column(name = "refunded_amount")
    private Long refundedAmount;
}
