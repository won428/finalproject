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
@ToString(exclude = {"board", "boardStatus", "author"})
@Entity
@Table(
        name = "post",
        indexes = {
                @Index(name = "ix_post_board_created", columnList = "board_id, created_at"),
                @Index(name = "ix_post_board_status_created", columnList = "board_id, status_code, created_at")
        }
)
// [핵심] 부모의 "id" 컬럼 매핑을 "post_id"로 재정의
@AttributeOverride(name = "id", column = @Column(name = "post_id"))
public class Post extends BasePkEntity {


    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "status_code", nullable = false, length = 30)
    private String statusCode;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    // [2] 일반 필드
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    // [3] Default Value 설정 (DDL 반영)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "view_count", nullable = false)
    @ColumnDefault("0")
    private Integer viewCount = 0;

    @Column(name = "like_count", nullable = false)
    @ColumnDefault("0")
    private Integer likeCount = 0;

    @Column(name = "comment_count", nullable = false)
    @ColumnDefault("0")
    private Integer commentCount = 0;

    @Column(name = "answer_count", nullable = false)
    @ColumnDefault("0")
    private Integer answerCount = 0;

    @Column(name = "accepted_comment_id")
    private Long acceptedCommentId;

    // ==========================================
    // [연관관계 매핑] - 읽기 전용
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "board_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_post_board")
    )
    private Board board;

    // [복합키 FK 매핑]
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns(
            foreignKey = @ForeignKey(name = "fk_post_board_status"),
            value = {
                    @JoinColumn(
                            name = "board_id",
                            referencedColumnName = "board_id",
                            insertable = false,
                            updatable = false
                    ),
                    @JoinColumn(
                            name = "status_code",
                            referencedColumnName = "status_code",
                            insertable = false,
                            updatable = false
                    )
            }
    )
    private BoardStatus boardStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "author_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_post_author")
    )
    private User author;
}
