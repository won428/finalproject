package com.example.demo.entity;

import com.example.demo.entity.base.BaseAttEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"post"})
@Entity
@Table(
        name = "post_attachment",
        indexes = {
                @Index(name = "ix_attachment_temp", columnList = "is_temp, created_at")
        }
)
@AttributeOverrides({
        // BasePkEntity.id -> attachment_id
        @AttributeOverride(name = "id", column = @Column(name = "attachment_id")),

        // BaseAttEntity.fileSizeBytes(file_size_bytes) -> file_size
        @AttributeOverride(name = "fileSizeBytes", column = @Column(name = "file_size", nullable = false, updatable = false))
})
public class PostAtt extends BaseAttEntity {

    // post_id BIGINT NULL
    @Column(name = "post_id")
    private Long post_id;

    // file_url VARCHAR(500) NOT NULL
    @Column(name = "file_url", nullable = false, length = 500)
    private String file_url;

    // is_image TINYINT(1) NOT NULL DEFAULT 0
    @Column(name = "is_image", nullable = false)
    private Boolean is_image = Boolean.FALSE;

    // sort_order INT NOT NULL DEFAULT 0
    @Column(name = "sort_order", nullable = false)
    private Integer sort_order = 0;

    // is_temp TINYINT(1) NOT NULL DEFAULT 1
    @Column(name = "is_temp", nullable = false)
    private Boolean is_temp = Boolean.TRUE;

    // 읽기 전용 연관관계 (post_id 컬럼로만 쓰기)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "post_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_attachment_post")
    )
    private Post post;

    @PrePersist
    void prePersist() {
        // BaseAttEntity.createdAt은 @CreationTimestamp로 자동 세팅됨
        if (is_image == null) is_image = Boolean.FALSE;
        if (sort_order == null) sort_order = 0;
        if (is_temp == null) is_temp = Boolean.TRUE;
    }


}
