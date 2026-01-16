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
/* 게시글과 태그 크로스테이블 */
public class PostTag {
//    변수명	    내용	        규격	    제약조건
//    post_id	게시글 ID	BIGINT 	NOT NULL
//    tag_id	태그 ID	    BIGINT	NOT NULL



/*  [DDL]
CREATE TABLE post_tag (
post_id BIGINT NOT NULL,
tag_id  BIGINT NOT NULL,
PRIMARY KEY (post_id, tag_id),
CONSTRAINT fk_post_tag_post FOREIGN KEY (post_id) REFERENCES post(post_id),
CONSTRAINT fk_post_tag_tag  FOREIGN KEY (tag_id) REFERENCES tag(tag_id)
);

CREATE INDEX ix_post_tag_tag ON post_tag(tag_id, post_id);
 */
}
