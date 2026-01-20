package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"session", "senderUser"})
@Entity
@Table(
        name = "chat_message",
        indexes = {
                @Index(name = "idx_msg_session_created", columnList = "session_id, created_at"),
                @Index(name = "idx_msg_session_id", columnList = "session_id, id")
        }
)
public class ChatMessage extends BasePkEntity {

    // [1] session_id -> sessionId (CamelCase)
    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    // [2] sender_type -> senderType
    @Column(name = "sender_type", nullable = false, length = 20)
    private String senderType;

    // [3] sender_user_id -> senderUserId (Nullable)
    @Column(name = "sender_user_id")
    private Long senderUserId;

    // [4] message_type: Default 'TEXT'
    @Column(name = "message_type", nullable = false, length = 20)
    @ColumnDefault("'TEXT'")
    private String messageType = "TEXT";

    // [5] Content: TEXT 타입
    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    // [6] created_at: Default CURRENT_TIMESTAMP
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // ==========================================
    // [연관관계 매핑]
    // ==========================================

    // 세션이 삭제되면 메시지도 삭제됨 (ON DELETE CASCADE는 DDL에서 처리)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "session_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_msg_session")
    )
    private ChatSession session;

    // 유저가 탈퇴해도 메시지는 남음 (ON DELETE SET NULL은 DDL에서 처리)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sender_user_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_msg_sender_user")
    )
    private User senderUser;

    // @PrePersist 제거 (DB Default 및 Java 초기화로 대체)
}