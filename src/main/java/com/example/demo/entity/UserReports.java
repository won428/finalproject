package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"reporter", "post", "target"})
@Entity
@Table(
        name = "user_reports",
        indexes = {
                @Index(name = "idx_user_reports_reporter_id", columnList = "reporter_id"),
                @Index(name = "idx_user_reports_target_id", columnList = "target_id"),
                @Index(name = "idx_user_reports_post_id", columnList = "post_id"),
                @Index(name = "idx_user_reports_created_at", columnList = "created_at")
        }
)
/* 신고 내역 */
public class UserReports extends BasePkEntity {
//    변수명	            내용	                        규격	            제약조건
//    id	            테이블 PK	                BIGINT	        PK, AUTO_INCREMENT
//    reporter_id	    신고자 ID	                BIGINT	        FK, NOT NULL
//    post_id	        게시글 ID	                BIGINT	        NULL
//    target_id	        신고 당한 사람 ID	            BIGINT	        NULL
//    reason	        사유(라디오 버튼 형태)	        VARCHAR(20)	        NOT NULL
//    description	    설명(신고하는 사유 부가설명)	VARCHAR(500)	NOT NULL
//    created_at	    신고 날짜	                DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP

    // reporter_id BIGINT NOT NULL
    @Column(name = "reporter_id", nullable = false)
    private Long reporter_id;

    // post_id BIGINT NULL
    @Column(name = "post_id")
    private Long post_id;

    // target_id BIGINT NULL
    @Column(name = "target_id")
    private Long target_id;

    // reason VARCHAR(20) NOT NULL
    @Column(name = "reason", nullable = false, length = 20)
    private String reason;

    // description VARCHAR(500) NOT NULL
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    // created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    // --- 연관관계(단방향, 읽기 전용) ---

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "reporter_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_user_reports_reporter")
    )
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "post_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_user_reports_post")
    )
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "target_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_user_reports_target")
    )
    private User target;

    @PrePersist
    void prePersist() {
        // ddl-auto=create에서 NULL로 들어가 NOT NULL 깨지는 케이스 방지
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }
    }

}
