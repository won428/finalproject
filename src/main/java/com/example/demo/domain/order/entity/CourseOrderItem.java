package com.example.demo.domain.order.entity;
import com.example.demo.base.BasePkEntity;
import com.example.demo.domain.course.entity.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"course", "order"})
@Entity
@Table(name = "course_order_items")
/* 강의 주문 상품 */
public class CourseOrderItem extends BasePkEntity {
//    변수명	        내용	            규격	        제약조건
//    id	        테이블 PK	    BIGINT	    PK
//    order_id  	주문 FK	        BIGINT	    FK
//    course_id 	강의 FK	        BIGINT	    FK
//    order_price	결제 당시 금액	BIGINT	    NOT NULL

    // FK 컬럼(쓰기용)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_order_item_course_order")
    )
    private Order order;

    // FK 컬럼(쓰기용)
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "course_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_order_item_course")
    )
    private Course course;

    @Column(name = "order_price", nullable = false)
    private Long orderPrice;
}
