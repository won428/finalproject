package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"admin"})
@Entity
@Table(
        name = "support_closure",
        indexes = {
                @Index(name = "idx_closure_range", columnList = "start_at, end_at")
        }
)
public class SupportClosure extends BasePkEntity {

    // [1] start_at -> startAt (CamelCase)
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    // [2] end_at -> endAt (CamelCase)
    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "reason", nullable = false, length = 100)
    private String reason;

    // [3] admin_id -> adminId (CamelCase, Nullable)
    @Column(name = "admin_id", nullable = true)
    private Long adminId;

    // [4] created_at (DB Default 추가)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // [5] 연관관계 매핑 (FK 이름: fk_closure_created_by)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "admin_id",
            insertable = false, // adminId 필드가 값을 관리
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_closure_created_by") // DDL과 일치시킴
    )
    private User admin;

}