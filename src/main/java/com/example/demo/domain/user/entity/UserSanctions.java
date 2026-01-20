package com.example.demo.domain.user.entity;

import com.example.demo.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user", "admin"}) // 순환참조/LAZY 로딩 출력 방지
@Entity
@Table(
        name = "user_sanctions",
        indexes = {
                @Index(name = "idx_sanctions_user_expiry", columnList = "user_id, expired_at"),
                @Index(name = "fk_sanctions_admin", columnList = "admin_id")
        }
)
/* 유저 제한 상태(현재 상태만 저장, 제한 해제되면 삭제) */
public class UserSanctions extends BasePkEntity {
//    변수명	            내용              	규격	            제약조건
//    id	            고유 ID	            BIGINT	            PK
//    user_id	        제한 유저	        BIGINT	            FK, NOT NULL
//    admin_id	        제재를 가한 관리자ID	BIGINT	            FK, NOT NULL
//  sanction_type	    제재 타입            VARCHAR(20)	        NOT NULL
//                      'BAN_7_DAYS, BAN_PERMANENT, MUTE 등’
//  reason	            제재 사유	        TEXT	           NULL
//  started_at	        제재 시작일	        DATETIME	        NOT NULL DEFAULT CURRENT_TIMESTAMP
//  expired_at	        제재 종료일	        DATETIME	        NOT NULL
//  released_at	        관리자가 수동으로 중도해제 했을 경우 그 시각	DATETIME	NULL

    // user_id BIGINT NOT NULL
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    // admin_id BIGINT NOT NULL
    @Column(name = "admin_id", nullable = false)
    private Long admin_id;

    // sanction_type VARCHAR(20) NOT NULL
    @Column(name = "sanction_type", nullable = false, length = 20)
    private String sanction_type;

    // reason TEXT NULL
    @Lob
    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    // started_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    @Column(name = "started_at", nullable = false)
    private LocalDateTime started_at;

    // expired_at DATETIME NOT NULL
    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expired_at;

    // released_at DATETIME NULL
    @Column(name = "released_at")
    private LocalDateTime released_at;

    // --- 연관관계(단방향, 읽기 전용) ---

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_sanctions_user")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "admin_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_sanctions_admin")
    )
    private User admin;

    @PrePersist
    void prePersist() {
        // ddl-auto=create에서 NULL로 들어가 NOT NULL 깨지는 케이스 방지
        if (started_at == null) {
            started_at = LocalDateTime.now();
        }
    }

}
