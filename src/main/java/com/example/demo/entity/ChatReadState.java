package com.example.demo.entity;

import com.example.demo.entity.base.BaseTimeEntity;
import com.example.demo.entity.base.BaseTimeNoPkEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"session", "readerUser", "lastReadMessage"})
@Entity
@Table(
        name = "chat_read_state",
        indexes = {
                @Index(name = "idx_read_state_last_msg", columnList = "last_read_message_id"),
                @Index(name = "idx_read_state_reader", columnList = "reader_user_id, session_id")
        }
)
@IdClass(ChatReadState.ChatReadStateId.class)
// [핵심] 상속받은 BaseTimeEntity 필드의 DDL 정의 재정의 (ON UPDATE 구문 반영)
@AttributeOverrides({
        // created_at: DEFAULT CURRENT_TIMESTAMP 명시
        @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")),

        // updated_at: ON UPDATE CURRENT_TIMESTAMP 명시
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"))
})
public class ChatReadState extends BaseTimeNoPkEntity {

    // [1] 복합키 1: session_id -> sessionId
    @Id
    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    // [2] 복합키 2: reader_user_id -> readerUserId
    @Id
    @Column(name = "reader_user_id", nullable = false)
    private Long readerUserId;

    // [3] last_read_message_id -> lastReadMessageId (Nullable)
    @Column(name = "last_read_message_id")
    private Long lastReadMessageId;

    // [4] last_read_at -> lastReadAt
    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt;

    // BaseTimeEntity 상속으로 created_at, updated_at 처리됨

    // ==========================================
    // [연관관계 매핑]
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "session_id",
            insertable = false, // @Id sessionId가 관리
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_read_state_session")
    )
    private ChatSession session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "reader_user_id",
            insertable = false, // @Id readerUserId가 관리
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_read_state_reader")
    )
    private User readerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "last_read_message_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_read_state_last_message")
    )
    private ChatMessage lastReadMessage;

    // [식별자 클래스] Lombok 적용으로 간소화
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ChatReadStateId implements Serializable {
        private Long sessionId;      // ChatReadState 변수명과 일치해야 함
        private Long readerUserId;   // ChatReadState 변수명과 일치해야 함
    }
}