package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import com.example.demo.enums.Action;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"course", "changedBy"})
@Entity
@Table(
        name = "course_history",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_history_course_revision",
                        columnNames = {"course_id", "revision_no"}
                )
        }
//        indexes = {
//                @Index(name = "idx_course_history_course", columnList = "course_id"),
//                @Index(name = "idx_course_history_changed_at", columnList = "changed_at"),
//                @Index(name = "idx_course_history_changed_by", columnList = "changed_by")
//        }  [flyway로 생성]
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseHistory extends BasePkEntity {
//    변수명	            내용	                규격	        제약조건
//    id	            테이블 PK	        BIGINT	    PK
//    course_id     	강의 id	            BIGINT	    FK
//    revision_no	    버젼	                BIGINT	    NOT NULL DEFAULT 1
//    action	        변경 사유(            ENUM	    NOT NULL DEFAULT CREATE
//                      create
//                      update
//                      )
//    changed_at	    변경 날짜	        DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    changed_by	    변경한 사람	        BIGINT	    FK, NOT NULL
//    price	            가격	                BIGINT	    NOT NULL
//    course_name	    현재 버젼 강의명	            VARCHAR	    NOT NULL

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false, foreignKey = @ForeignKey(name = "fk_course_history_course"))
    private Course course;

    @Column(name = "revision_no", nullable = false)
    private Long revisionNo;

    @Column(name = "action", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Action action = Action.CREATE;

    @CreationTimestamp
    @Column(name = "changed_at", updatable = false, nullable = false) // 수정 시에는 건드리지 않음
    private LocalDateTime changedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "changed_by", nullable = false, foreignKey = @ForeignKey(name = "fk_course_history_user") )
    private User changedBy;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

}
