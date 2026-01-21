package com.example.demo.domain.chat.entity;

import com.example.demo.base.entity.BaseTimeEntity;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.counselor.entity.CounselorProfile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user", "counselorProfile"})
@Entity
@Table(
        name = "chat_session",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_session_open_key", columnNames = {"open_key"})
        },
        indexes = {
                @Index(name = "idx_session_user_created", columnList = "user_id, created_at"),
                @Index(name = "idx_session_counselor_created", columnList = "counselor_id, created_at"),
                @Index(name = "idx_session_status", columnList = "status"),
                @Index(name = "idx_session_status_queued", columnList = "status, queued_at"),
                @Index(name = "idx_session_counselor_status", columnList = "counselor_id, status, created_at")
        }
)
// [핵심] 상속받은 BaseTimeEntity 필드의 DDL 정의를 목표 테이블에 맞춰 재정의
@AttributeOverrides({
        // BasePkEntity의 id (그대로 사용)
        @AttributeOverride(name = "id", column = @Column(name = "id")),

        // created_at: DEFAULT CURRENT_TIMESTAMP 명시
        @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")),

        // updated_at: ON UPDATE CURRENT_TIMESTAMP 명시
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"))
})
public class ChatSession extends BaseTimeEntity {

    // [1] user_id -> userId
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // [2] counselor_id -> counselorId (Nullable)
    @Column(name = "counselor_id")
    private Long counselorId;

    // [3] open_key -> openKey
    @Column(name = "open_key", nullable = false, length = 36, unique = true)
    private String openKey;

    // [4] status: Default 'WAITING'
    @Column(name = "status", nullable = false, length = 20)
    @ColumnDefault("'WAITING'")
    private String status = "WAITING"; // 자바 객체용 초기값

    @Column(name = "close_reason", length = 50)
    private String closeReason;

    @Column(name = "ended_by", length = 20)
    private String endedBy;

    // [5] queued_at: Default CURRENT_TIMESTAMP
    // 대기열 진입 시간은 생성 시간과 거의 같으므로 CreationTimestamp 활용 가능
    // 혹은 @PrePersist에서 LocalDateTime.now() 주입
    @CreationTimestamp
    @Column(name = "queued_at", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime queuedAt;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    // ==========================================
    // [연관관계 매핑]
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_session_user")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "counselor_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_session_counselor")
    )
    private CounselorProfile counselorProfile;

    // UUID 자동 생성 (Insert 전 실행)
    @PrePersist
    void prePersist() {
        if (this.openKey == null) {
            this.openKey = UUID.randomUUID().toString();
        }
    }
}