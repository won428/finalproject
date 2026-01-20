package com.example.demo.domain.entitlement.entity;

import com.example.demo.base.BaseTimeEntity;
import com.example.demo.domain.course.entity.Course;
import com.example.demo.domain.order.entity.CourseOrderItem;
import com.example.demo.domain.user.entity.User;
import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"course","user"})
@Entity
@Table(
        name = "user_entitlements"
)
/* 강의 접근 권한 */
public class UserEntitlement extends BaseTimeEntity {
//    변수명	            내용	            규격	        제약조건
//    id	            테이블 PK	    BIGINT	    PK
//    course_id	        강의 FK	        BIGINT	    FK, NOT NULL
//    course_order_item_id 주문 내역     BIGINT      FK, NOT NULL
//    user_id	        유저 FK	        BIGINT	    FK, NOT NULL
//    status	        상태(승인, 취소)	ENUM	    NOT NULL DEFAULT APPROVED
//    created_at	    생성 날짜	    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at        수정 날짜        DATETIME
//    revoked_at        회수 날짜        DATETIME
//    revoke_reason     회수 이유        VARCHAR(200)

    // FK 컬럼(쓰기용)
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "course_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_user_entitlements_course")
    )
    private Course course;

    // FK 컬럼(쓰기용)
    @Column(name = "course_order_item_id", nullable = false)
    private Long courseOrderItemId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "course_order_item_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_user_entitlements_course_order_item")
    )
    private CourseOrderItem courseOrderItem;

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
            foreignKey = @ForeignKey(name = "fk_user_entitlements_user")
    )
    private User user;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.APPROVED;

    @Column(name = "revoked_at", nullable = true)
    private LocalDateTime revokedAt;

    @Column(name = "revoke_reason", nullable = true, length = 200)
    private String revokeReason;


/*  [comments]
결제 완료가 되면 해당 강의에 대한 접근 권한을 유저에게 부여하고,
환불이나 취소등 절차가 진행되면 해당 권한을 취소하는 방식으로 로직이 짜여진다면 필요한 권한 테이블 입니다.
 */
}
