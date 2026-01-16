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
