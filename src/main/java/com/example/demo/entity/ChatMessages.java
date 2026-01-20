package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"session", "sender_user"})
@Entity
@Table(
        name = "chat_message",
        indexes = {
                @Index(name = "idx_msg_session_created", columnList = "session_id, created_at"),

                // ✅ 추가: 세션별 페이징/마지막 메시지 조회 최적화
                @Index(name = "idx_msg_session_id", columnList = "session_id, id")
        }
)
/*  */
public class ChatMessages extends BasePkEntity {

    @Column(name = "session_id", nullable = false)
    private Long session_id;

    @Column(name = "sender_type", nullable = false, length = 20)
    private String sender_type;

    @Column(name = "sender_user_id")
    private Long sender_user_id;

    @Column(name = "message_type", nullable = false, length = 20)
    private String message_type;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "session_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_msg_session")
    )
    private ChatSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sender_user_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_msg_sender_user")
    )
    private User sender_user;

    @PrePersist
    void prePersist() {
        if (message_type == null) message_type = "TEXT";
    }
//    변수명	            내용	                                                규격	            제약조건

//    id	            고유ID	                                            BIGINT	        PK, AUTO_INCREMENT
//    session_id	    세션ID	                                            BIGINT	        FK, NOT NULL
//    sender_type	    메시지 전송자
//                      USER/COUNSELOR/SYSTEM	                            VARCHAR(20)	    NOT NULL

//    sender_user_id	USER/COUNSELOR일 때 users.id, SYSTEM이면 NULL	    BIGINT	        FK, NULL
//    message_type	    TEXT/SYSTEM/IMAGE/FILE	                            VARCHAR(20)	    NOT NULL DEFAULT 'TEXT’
//    content	        메시지 내용	                                        TEXT	        NOT NULL
//    created_at	    행 생성일	                                        DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP


/*  [DDL]
CREATE TABLE chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    sender_type VARCHAR(20) NOT NULL,  -- USER/COUNSELOR/SYSTEM
    sender_user_id BIGINT NULL,        -- SYSTEM이면 NULL

    message_type VARCHAR(20) NOT NULL DEFAULT 'TEXT',
    content TEXT NOT NULL,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_msg_session
        FOREIGN KEY (session_id) REFERENCES chat_session(id) ON DELETE CASCADE,
    CONSTRAINT fk_msg_sender_user
        FOREIGN KEY (sender_user_id) REFERENCES users(id) ON DELETE SET NULL,

    INDEX idx_msg_session_created (session_id, created_at),
    INDEX idx_msg_session_id (session_id, id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
*/
}
