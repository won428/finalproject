package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"post", "author", "parent_comment"})
@Entity
@Table(
        name = "comment",
        indexes = {
                @Index(name = "ix_comment_post_created", columnList = "post_id, created_at")
        }
)
/*  */
public class Comment {
//    변수명	            내용	                        규격	        제약조건
//    comment_id	    댓글 번호	                BIGINT	    PK,
//    comment.id        셀프참조(대댓글용 계층구조)
//    AUTO_INCREMENT
//    post_id	        게시글 번호	                BIGINT	    FK, NOT NULL
//    author_id	        작성자(users.id)	            BIGINT	    NOT NULL
//    parent_comment_id	대댓글 번호(트리형태)	        BIGINT	    NULL
//    content	        내용	LONGTEXT	                        NOT NULL
//    created_at	    게시일	                    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    deleted_at	    소프트 삭제용	                DATETIME	NULL

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long comment_id;

    @Column(name = "post_id", nullable = false)
    private Long post_id;

    @Column(name = "author_id", nullable = false)
    private Long author_id;

    @Column(name = "parent_comment_id")
    private Long parent_comment_id;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_answer", nullable = false)
    private Boolean is_answer = Boolean.FALSE;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    // 읽기 전용 연관관계
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "post_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_comment_post")
    )
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "author_id",
            nullable = false,
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
    private Comment parent_comment;

    @PrePersist
    void prePersist() {
        if (created_at == null) created_at = LocalDateTime.now();
        if (is_answer == null) is_answer = Boolean.FALSE;
    }


/*  [DDL]
CREATE TABLE comment (
	comment_id        BIGINT PRIMARY KEY AUTO_INCREMENT,
	post_id           BIGINT NOT NULL,
	author_id         BIGINT NOT NULL,
	parent_comment_id BIGINT NULL,
	content           TEXT NOT NULL,

	-- Q&A에서 “답변 댓글” 표시용(선택)
	is_answer TINYINT NOT NULL DEFAULT 0,

	created_at        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	deleted_at        DATETIME NULL,

	CONSTRAINT fk_comment_post
		FOREIGN KEY (post_id) REFERENCES post(post_id),
	CONSTRAINT fk_comment_parent
		FOREIGN KEY (parent_comment_id) REFERENCES comment(comment_id)
	);

CREATE INDEX ix_comment_post_created    ON comment(post_id, created_at);
*/
}
