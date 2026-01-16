package com.example.demo.entity;

import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "coupon_cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/* 쿠폰 보유 목록 */
public class CouponCart {
//    변수명	내용	규격	제약조건
//    id	        테이블 PK	BIGINT	    PK
//    user_id	    보유자 ID	BIGINT	    FK
//    coupon_id 	쿠폰 ID	    BIGINT	    FK
//    received_at	발급 날짜	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    status	    쿠폰 상태(    ENUM	    NOT NULL DEFAULT UNUSED
//                  미사용(UNUSED),
//                  사용(USED),
//                  만료(EXPIRED))

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long userId;

    private Long couponId;

    private LocalDate receivedAt;

    private Status staus;
}
