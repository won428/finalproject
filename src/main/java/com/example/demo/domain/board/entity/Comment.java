package com.example.demo.domain.board.entity;

import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"post", "author", "parentComment"}) // 연관관계 필드명 변경 반영
@Entity
@Table(
        name = "comment",
        indexes = {
                @Index(name = "ix_comment_post_created", columnList = "post_id, created_at")
        }
)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId; // Java: commentId, DB: comment_id

    // [1] FK용 컬럼 직접 매핑 (DB 컬럼명: post_id)
    @Column(name = "post_id", nullable = false)
    private Long postId;

    // [2] FK용 컬럼 직접 매핑 (DB 컬럼명: author_id)
    @Column(name = "author_id", nullable = false)
    private Long authorId;

    // [3] FK용 컬럼 직접 매핑 (DB 컬럼명: parent_comment_id)
    @Column(name = "parent_comment_id", nullable = true) // 부모 댓글 없을 수 있음
    private Long parentCommentId;

    // [4] Content: TEXT 타입 명시
    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    // [5] is_answer: TINYINT + Default 0 설정
    // Boolean false -> DB 0 저장
    @Column(name = "is_answer", nullable = false, columnDefinition = "TINYINT")
    @ColumnDefault("0")
    private Boolean isAnswer = false;

    // [6] created_at: DB Default 설정
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // ==========================================
    // [연관관계 매핑] - 읽기 전용 (insertable/updatable = false)
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "post_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_comment_post")
    )
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "author_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_comment_author")
    )
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "parent_comment_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_comment_parent")
    )
    private Comment parentComment;

    // @PrePersist 제거 (DB Default로 대체됨)
}
