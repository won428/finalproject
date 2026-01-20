package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"counselor"})
@Entity
@Table(name = "counselor_profile")
public class CounselorProfile {

    // [1] PK 설정: BasePkEntity 상속 금지 (Auto Increment 아님)
    // users 테이블의 id와 1:1로 매핑되므로 직접 값을 입력하거나 @MapsId 전략을 사용해야 함
    @Id
    @Column(name = "counselor_id")
    private Long counselorId;

    // [2] Default 3 설정
    @Column(name = "max_concurrent_chats", nullable = false)
    @ColumnDefault("3")
    private Integer maxConcurrentChats = 3;

    // [3] TINYINT(1) + Default 1 설정
    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("1")
    private Boolean isActive = true;

    // [4] created_at (BaseTimeEntity 대신 직접 정의하여 DDL 일치시킴)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // [5] updated_at (ON UPDATE 구문은 표준 JPA로 생성이 어려워 columnDefinition 사용)
    @UpdateTimestamp
    @Column(
            name = "updated_at",
            nullable = false,
            columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    // [6] 연관관계 매핑 (1:1)
    // 식별 관계이므로 MapsId를 쓰거나, PK에 값을 직접 넣어야 함.
    // 여기서는 PK 컬럼(counselorId)이 위에 있으므로 읽기 전용으로 매핑.
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "counselor_id",
            insertable = false, // PK인 counselorId가 값을 관리
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_counselor_profile_user")
    )
    @OnDelete(action = OnDeleteAction.CASCADE) // DDL에 ON DELETE CASCADE 반영을 위한 힌트
    private User counselor;

    // @PrePersist 제거 (DB Default로 대체)
}