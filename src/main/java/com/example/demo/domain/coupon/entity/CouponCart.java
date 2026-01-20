package com.example.demo.domain.coupon.entity;

import com.example.demo.base.BasePkEntity;
import com.example.demo.domain.user.entity.User;
import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"user", "coupon"})
@Entity
@Table(name = "coupon_cart",
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_coupon_cart_user_coupon", columnNames = {"user_id", "coupon_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/* 쿠폰 보유 목록 */
public class CouponCart extends BasePkEntity {
//    변수명	내용	규격	제약조건
//    id	        테이블 PK	BIGINT	    PK
//    user_id	    보유자 ID	BIGINT	    FK, NOT NULL
//    coupon_id 	쿠폰 ID	    BIGINT	    FK, NOT NULL
//    received_at	발급 날짜	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    status	    쿠폰 상태(    ENUM	    NOT NULL DEFAULT UNUSED
//                  미사용(UNUSED),
//                  사용(USED),
//                  만료(EXPIRED))

    // FK 컬럼(쓰기용)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_coupon_cart_users")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "coupon_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_coupon_cart_coupon")
    )
    private Coupon coupon;

    @CreationTimestamp
    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.UNUSED;
}
