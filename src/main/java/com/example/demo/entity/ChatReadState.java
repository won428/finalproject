package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"session", "reader_user", "last_read_message"})
@Entity
@Table(
        name = "chat_read_state",
        indexes = {
                @Index(name = "idx_read_state_last_msg", columnList = "last_read_message_id"),

                // ✅ 추가: 유저 기준 read_state 조회 최적화
                @Index(name = "idx_read_state_reader", columnList = "reader_user_id, session_id")
        }
)
@IdClass(ChatReadState.ChatReadStateId.class)
/*  */
public class ChatReadState {

    @Id
    @Column(name = "session_id", nullable = false)
    private Long session_id;

    @Id
    @Column(name = "reader_user_id", nullable = false)
    private Long reader_user_id;

    @Column(name = "last_read_message_id")
    private Long last_read_message_id;

    @Column(name = "last_read_at")
    private LocalDateTime last_read_at;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "session_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_read_state_session")
    )
    private ChatSession session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "reader_user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_read_state_reader")
    )
    private User reader_user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "last_read_message_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_read_state_last_message")
    )
    private ChatMessages last_read_message;

    // 단일 고유ID(PK)가 없고, (session_id + reader_user_id) 조합 PK라서 “이 엔티티의 PK가 무엇인지”를 알려주는 클래스
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ChatReadStateId implements Serializable {
        private Long session_id;
        private Long reader_user_id;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ChatReadStateId)) return false;
            ChatReadStateId that = (ChatReadStateId) o;
            return Objects.equals(session_id, that.session_id)
                    && Objects.equals(reader_user_id, that.reader_user_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(session_id, reader_user_id);
        }
    }

// 변수명	            내용	                            규격	        제약조건
//session_id	        채팅방 ID→ chat_session(id) 참조	    BIGINT	        PK, FK, NOT NULL
//reader_user_id	    읽는 사람(users.id)	                BIGINT	        PK, FK, NOT NULL
//last_read_message_id	마지막으로 읽은 메시지ID              BIGINT	        FK, NULL
//                      → chat_message(id) 참조
//last_read_at	        마지막으로 읽은 시각	                DATETIME	    NULL
//created_at	        행이 생성된 시각	                    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//updated_at	        행이 수정된 시각	                    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

/*
CREATE TABLE chat_read_state (
    session_id BIGINT NOT NULL,
    reader_user_id BIGINT NOT NULL,
    last_read_message_id BIGINT NULL, -- 읽은 게 없으면 NULL

    last_read_at DATETIME NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- 복합 PK: (세션+유저)는 유일해야 함
    PRIMARY KEY (session_id, reader_user_id),

    CONSTRAINT fk_read_state_session
        FOREIGN KEY (session_id) REFERENCES chat_session(id) ON DELETE CASCADE,

    CONSTRAINT fk_read_state_reader
        FOREIGN KEY (reader_user_id) REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT fk_read_state_last_message
        FOREIGN KEY (last_read_message_id) REFERENCES chat_message(id) ON DELETE SET NULL,

    INDEX idx_read_state_last_msg (last_read_message_id),
    INDEX idx_read_state_reader (reader_user_id, session_id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
*/
}
