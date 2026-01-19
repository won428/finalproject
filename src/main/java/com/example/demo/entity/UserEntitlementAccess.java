package com.example.demo.entity;

import com.example.demo.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"course", "courseOrderItem", "user"})
@Entity
@Table(
        name = "user_entitlement_access",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_entitlement_access_course_user",
                        columnNames = {"course_id", "user_id"}
                ),
                @UniqueConstraint(
                        name = "uk_user_entitlement_access_order_item",
                        columnNames = {"course_order_item_id"}
                )
        }
)
/* 강의 현재 접근 권한(활성) */
public class UserEntitlementAccess extends BaseTimeEntity {
//    변수명	                내용	                    규격	        제약조건
//    id	                테이블 PK	            BIGINT	    PK
//    course_id	            강의 FK	                BIGINT	    FK, NOT NULL
//    course_order_item_id    권한 부여 근거(주문항목) BIGINT	    FK, NOT NULL, UNIQUE
//    user_id	                유저 FK	                BIGINT	    FK, NOT NULL
//    granted_at	            권한 부여 시각	        DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    created_at	            생성 날짜	            DATETIME	(BaseTimeEntity)
//    updated_at               수정 날짜	            DATETIME	(BaseTimeEntity)

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "course_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_entitlement_access_course")
    )
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "course_order_item_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_entitlement_access_course_order_item")
    )
    private CourseOrderItem courseOrderItem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_entitlement_access_user")
    )
    private User user;

    @Column(name = "granted_at", nullable = false)
    private LocalDateTime grantedAt = LocalDateTime.now();
}
