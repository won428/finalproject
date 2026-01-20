package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"board", "board_status", "author"})
@Entity
@Table(
        name = "post",
        indexes = {
                // JPA @Index는 DESC 표현 못함 → DESC 필요하면 Flyway에서
                @Index(name = "ix_post_board_created", columnList = "board_id, created_at"),
                @Index(name = "ix_post_board_status_created", columnList = "board_id, status_code, created_at")
        }
)
/* 게시글 */
public class Post {
//    변수명	내용	규격	제약조건
//    post_id	    게시글 번호	           BIGINT 	        PK AUTO_INCREMENT
//    board_id	    게시판번호               BIGINT 	        FK1, FK2 NOT NULL
//                  1 - STUDY
//                  2 - RECRUIT 등

//    status_code	게시글 상태              VARCHAR(30)	    FK2 NOT NULL
//                  모집중 / 모집완료
//                  미답변 / 답변완료
//                  해당없음 등

//    author_id	    게시글 작성자	            BIGINT 	        NOT NULL
//    title	        제목	                    VARCHAR(200)	NOT NULL
//    content	    본문	                    MEDIUMTEXT	    NOT NULL
//    created_at	게시일	                DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	수정일	                DATETIME	    NULL
//    deleted_at	삭제일(소프트 삭제용)	    DATETIME	    NULL
//    view_count	조회수	                INT         	NOT NULL DEFAULT 0
//    like_count	좋아요 수	            INT	            NOT NULL DEFAULT 0
//    comment_count	댓글 수	                INT	NOT         NULL DEFAULT 0

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long post_id;

    @Column(name = "board_id", nullable = false)
    private Long board_id;

    @Column(name = "status_code", nullable = false, length = 30)
    private String status_code;

    @Column(name = "author_id", nullable = false)
    private Long author_id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    @Column(name = "view_count", nullable = false)
    private Integer view_count = 0;

    @Column(name = "like_count", nullable = false)
    private Integer like_count = 0;

    @Column(name = "comment_count", nullable = false)
    private Integer comment_count = 0;

    @Column(name = "answer_count", nullable = false)
    private Integer answer_count = 0;

    @Column(name = "accepted_comment_id")
    private Long accepted_comment_id;

    // 읽기 전용 연관관계
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "board_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_post_board")
    )
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns(
            foreignKey = @jakarta.persistence.ForeignKey(name = "fk_post_board_status"),
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
    private BoardStatus board_status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "author_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_post_author")
    )
    private User author;

    @PrePersist
    void prePersist() {
        if (created_at == null) created_at = LocalDateTime.now();
        if (view_count == null) view_count = 0;
        if (like_count == null) like_count = 0;
        if (comment_count == null) comment_count = 0;
        if (answer_count == null) answer_count = 0;
    }

/*  [DDL]
CREATE TABLE post (
	post_id        BIGINT PRIMARY KEY AUTO_INCREMENT,
	board_id       BIGINT NOT NULL,
	status_code    VARCHAR(30) NOT NULL,

	author_id      BIGINT NOT NULL,
	title          VARCHAR(200) NOT NULL,
	content        MEDIUMTEXT NOT NULL,

	created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at     DATETIME NULL,
	deleted_at     DATETIME NULL,

	view_count     INT NOT NULL DEFAULT 0,
	like_count     INT NOT NULL DEFAULT 0,
	comment_count  INT NOT NULL DEFAULT 0,

	- - Q&A 확장(선택)
	answer_count INT NOT NULL DEFAULT 0,
	accepted_comment_id BIGINT NULL,

	CONSTRAINT fk_post_board
	FOREIGN KEY (board_id) REFERENCES board(board_id),

	- - ★ 핵심: 이 게시판에서 허용된 status_code만 저장 가능
	CONSTRAINT fk_post_board_status
	FOREIGN KEY (board_id, status_code)
	REFERENCES board_status(board_id, status_code)
);

CREATE INDEX ix_post_board_created        ON post(board_id, created_at DESC);
CREATE INDEX ix_post_board_status_created ON post(board_id, status_code, created_at DESC);
 */
}
