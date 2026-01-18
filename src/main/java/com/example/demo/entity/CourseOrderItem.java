package com.example.demo.entity;
import com.example.demo.entity.base.BasePkEntity;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_item_course_order"))
    private CourseOrder order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_item_course"))
    private Course course;

    @Column(name = "order_price", nullable = false)
    private Long orderPrice;
}
