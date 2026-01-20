package com.example.demo.entity;

import com.example.demo.entity.base.BaseTagEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "mentoring_post_tags")
/* 멘토링 글 태그 */
public class MentoringPostTag extends BaseTagEntity {
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
            foreignKey = @ForeignKey(name = "fk_mpt_post")
    )
    private MentoringPost mentoringPost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tag_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_mpt_tag")
    )
    private Tag tag;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MentoringPostTagId implements Serializable {
        private Long postId;
        private Long tagId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MentoringPostTagId)) return false;
            MentoringPostTagId that = (MentoringPostTagId) o;
            return Objects.equals(postId, that.postId)
                    && Objects.equals(tagId, that.tagId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(postId, tagId);
        }
    }
}