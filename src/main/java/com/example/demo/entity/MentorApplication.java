package com.example.demo.entity;

import com.example.demo.entity.base.BaseRequestTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"user", "processedBy"})
@Entity
@Table(name = "mentor_applications")
/* 멘토 신청 내역 */
public class MentorApplication extends BaseRequestTimeEntity {
//    변수명	            내용	        규격	        제약조건
//    id	            테이블 PK	BIGINT	    PK
//    user_id	        신청자 ID	BIGINT	    FK NOT NULL
//    status	        상태	        ENUM	    NOT NULL DEFAULT PENDING
//    submitted_at	    신청 일자	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    reviewed_at	    처리 일자	DATETIME
//    processed_by	    처리자 ID	BIGINT	    FK
//    reject_reason	    반려사유	    VARCHAR

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
            foreignKey = @ForeignKey(name = "fk_mentor_application_user")
    )
    private User user;

    // FK 컬럼(쓰기용) - nullable
    @Column(name = "processed_by")
    private Long processedById;

    // 연관관계(읽기전용) - nullable
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "processed_by",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_mentor_application_processed_by")
    )
    private User processedBy;

    @Column(name = "reject_reason", length = 500)
    private String rejectReason;

}
