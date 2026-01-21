package com.example.demo.domain.board.entity;

import com.example.demo.base.entity.BaseAttEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"post"}) // 연관관계 필드 제외
@Entity
@Table(
        name = "post_attachment",
        indexes = {
                @Index(name = "ix_attachment_temp", columnList = "is_temp, created_at")
        }
)
@AttributeOverrides({
        // [1] PK 매핑
        @AttributeOverride(name = "id", column = @Column(name = "attachment_id")),

        // [2] BaseAttEntity 필드 재정의 (이름, 길이, 속성 맞춤)
        @AttributeOverride(name = "fileSizeBytes", column = @Column(name = "file_size", nullable = false)),

        // originalFileName -> original_name으로 변경
        @AttributeOverride(name = "originalFileName", column = @Column(name = "original_name", nullable = false)),

        // s3Key 길이 255로 제한 (DDL 반영)
        @AttributeOverride(name = "s3Key", column = @Column(name = "s3_key", length = 255, nullable = false)),

        // contentType 길이 100으로 제한 (DDL 반영)
        @AttributeOverride(name = "contentType", column = @Column(name = "content_type", length = 100, nullable = false))
})
public class PostAtt extends BaseAttEntity {

    // [3] post_id 컬럼 직접 매핑 (읽기 전용 아님, FK용 컬럼)
    // 연관관계 필드와 동시에 쓸 때는 insertable/updatable 주의가 필요하나,
    // 보통 FK ID만으로 저장하는 로직이 있다면 이쪽을 살리고 연관관계를 읽기 전용으로 둡니다.
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;

    // [4] Boolean -> TINYINT(1) & Default 설정
    @Column(name = "is_image", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("0")
    private Boolean isImage = false;

    @Column(name = "sort_order", nullable = false)
    @ColumnDefault("0")
    private Integer sortOrder = 0;

    @Column(name = "is_temp", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("1")
    private Boolean isTemp = true;

    // [5] 연관관계 매핑 (FK 제약조건 이름 지정, 읽기/쓰기 분리 시 읽기 전용 설정)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "post_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_attachment_post")
    )
    private Post post;

    // @PrePersist 제거 (초기화 및 @ColumnDefault로 대체)
}