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
/*  */
public class Coupon {
//    변수명	            내용	        규격	        제약조건
//    id	            테이블 PK	BIGINT	    PK
//    coupon_name	    쿠폰명	    VARCHAR	    NOT NULL, UNIQUE
//    discount	        할인율	    BIGINT	    NOT NULL
//    expiration_date	유효 기간	DATETIME	NOT NULL
}
