package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user","admin"}) // 순환참조/지연로딩 출력 방지
@Entity
@Table(name = "user_status_history")
/* 사용자의 상태 관리 */
public class UserStatusHistory extends BasePkEntity {
//    변수명	내용	규격	제약조건
//    id		                                        BIGINT	        PK AUTO_INCREMENT
//    user_id	        상태 변경된 유저의 ID	            BIGINT	        FK, NOT NULL
//    prev_status	    변경 전 상태	                    VARCHAR(20)	    NOT NULL
//    new_status	    변경 후 상태	                    VARCHAR(20)	    NOT NULL
//    changed_by	    누가 상태를 변경했는지(관리자의 ID)	BIGINT	        NULL
//    change_reason	    변경 사유	                    VARCHAR(500)	NULL
//    changed_at	    변경된 시각	                    DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP

    // user_id BIGINT NOT NULL, FK -> users(id) ON DELETE CASCADE
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    // 대상 유저 연관관계 (같은 user_id 컬럼 사용, 읽기 전용 매핑)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_status_history_user")
    )
    private User user;

    // prev_status VARCHAR(20) NOT NULL
    @Column(name = "prev_status", nullable = false, length = 20)
    private String prev_status;

    // new_status VARCHAR(20) NOT NULL
    @Column(name = "new_status", nullable = false, length = 20)
    private String new_status;

    // change_reason VARCHAR(500) NULL
    @Column(name = "change_reason", length = 500)
    private String change_reason;

    // changed_by BIGINT NULL, FK -> users(id) ON DELETE SET NULL
    @Column(name = "changed_by")
    private Long changed_by;

    // 처리 관리자 연관관계 (같은 changed_by 컬럼 사용, 읽기 전용 매핑)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "changed_by",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_status_history_admin")
    )
    private User admin;

    // changed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changed_at;

    @PrePersist
    void prePersist() {
        // ddl-auto=create에서 NULL 들어가 NOT NULL 깨지는 케이스 방지
        if (changed_at == null) {
            changed_at = LocalDateTime.now();
        }
    }

/*  [comments]
1. 증거가 필요하다면 첨부파일 테이블 사용.

2. 실시간 채팅상담 시 부재중인 상담사의 세션연결 필터링 + 일반 학습자, 교육자의 계정 삭제(소프트 삭제), 계정 비활성화 관리 시에 사용되는 DB
 */
}
