package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "")
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
