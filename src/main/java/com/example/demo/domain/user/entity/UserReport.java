package com.example.demo.domain.user.entity;

import com.example.demo.base.entity.BasePkEntity;
import com.example.demo.domain.board.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

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
// [핵심] DB 레벨의 CHECK 제약조건 추가
@Check(name = "ck_user_reports_target_at_least_one", constraints = "post_id IS NOT NULL OR target_id IS NOT NULL")
public class UserReport extends BasePkEntity {

    // [1] reporter_id -> reporterId (CamelCase)
    @Column(name = "reporter_id", nullable = false)
    private Long reporterId;

    // [2] post_id -> postId (Nullable)
    @Column(name = "post_id")
    private Long postId;

    // [3] target_id -> targetId (Nullable)
    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "reason", nullable = false, length = 20)
    private String reason;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    // [4] created_at: Default CURRENT_TIMESTAMP
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // ==========================================
    // [연관관계 매핑]
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "reporter_id",
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
}