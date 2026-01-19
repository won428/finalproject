package com.example.demo.entity;

import com.example.demo.entity.base.BaseRequestTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"user","reviewerAdmin"})
@Entity
@Table(name = "expert_applications")
/* 전문가 신청 내역 */
public class ExpertApplication extends BaseRequestTimeEntity {
//    변수명	            내용	        규격	        제약조건
//    id	            테이블 ID	BIGINT	    PK
//    user_id	        신청자 ID	BIGINT	    FK NOT NULL
//    status	        상태	        ENUM	    NOT NULL DEFAULT PENDING
//    submitted_at	    제출 시간	DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    reviewed_at	    처리 시간	DATETIME
//    reviewer_admin_id	처리자 ID	BIGINT	    FK NOT NULL
//    reject_reason	    반려 사유	VARCHAR

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_expert_applications_user") )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "reviewer_admin_id", nullable = true, foreignKey = @ForeignKey(name = "fk_expert_applications_reviewer_admin") )
    private User reviewerAdmin;

    @Column(name="reject_reason", nullable = true, length = 200)
    private String rejectReason;
}
