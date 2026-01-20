package com.example.demo.domain.order.entity;
import com.example.demo.base.BasePkEntity;
import com.example.demo.domain.mentoring.entity.MentoringPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"mentoringPost", "order"})
@Entity
@Table(name = "mentoring_order_items")
/* 멘토링 주문 상품 */
public class MentoringOrderItem extends BasePkEntity {
//    변수명	        내용	            규격	        제약조건
//    id	        테이블 PK	    BIGINT	    PK
//    order_id  	주문 FK	        BIGINT	    FK
//    mentoring_post_id 	멘토링 글 FK	        BIGINT	    FK
//    order_price	결제 당시 금액	BIGINT	    NOT NULL

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
            foreignKey = @ForeignKey(name = "fk_mentoring_order_items_order")
    )
    private Order order;

    // FK 컬럼(쓰기용)
    @Column(name = "mentoring_post_id", nullable = false)
    private Long mentoringPostId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "mentoring_post_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_mentoring_order_items_mentoring_post")
    )
    private MentoringPost mentoringPost;

    @Column(name = "order_price", nullable = false)
    private Long orderPrice;
}
