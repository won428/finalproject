package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/* 쿠폰 목록 */
public class Coupon extends BaseTimeEntity{
//    변수명	            내용	        규격	        제약조건
//    id	            테이블 PK	BIGINT	    PK
//    coupon_name	    쿠폰명	    VARCHAR	    NOT NULL, UNIQUE
//    discount	        할인율	    INT	    NOT NULL
//    expiration_date	유효 기간	DATETIME	NOT NULL

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "coupon_name", nullable = false, unique = true)
    private String couponName;

    @Column(name = "discount", nullable = false)
    @Min(1)
    @Max(100)
    private Integer discount;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;



}
