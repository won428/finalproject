package com.example.demo.domain.board.entity;

import com.example.demo.base.BaseTagEntity;
import com.example.demo.domain.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"post", "tag"})
@Entity
@Table(
        name = "post_tag",
        indexes = {
                @Index(name = "ix_post_tag_tag", columnList = "tag_id, post_id")
        }
)
@IdClass(PostTag.PostTagId.class)
/* 게시글과 태그 크로스테이블 */
public class PostTag extends BaseTagEntity {
//    변수명	    내용	        규격	    제약조건
//    post_id	게시글 ID	BIGINT 	NOT NULL
//    tag_id	태그 ID	    BIGINT	NOT NULL

    // 읽기 전용 연관관계(컬럼 쓰기 주체는 BaseTagEntity의 postId/tagId)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "post_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_post_tag_post")
    )
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tag_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_post_tag_tag")
    )
    private Tag tag;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor // 편리한 생성자 추가
    @EqualsAndHashCode  // equals, hashCode 로직 자동 생성
    public static class PostTagId implements Serializable {
        private Long postId;
        private Long tagId;
    }

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
