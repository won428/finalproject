package com.example.demo.domain.counselor.entity;

import com.example.demo.base.BaseTimeEntity;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"counselorProfile", "createdByUser"})
@Entity
@Table(
        name = "counselor_absence",
        indexes = {
                @Index(name = "idx_absence_counselor_range", columnList = "counselor_id, start_at, end_at")
        }
)
// [핵심] 상속받은 필드들의 컬럼 속성 재정의 (DDL 강제 맞춤)
@AttributeOverrides({
        // BasePkEntity의 id -> DDL과 일치 (변경 없음, 명시적 확인)
        @AttributeOverride(name = "id", column = @Column(name = "id")),

        // BaseTimeEntity의 createdAt -> DEFAULT CURRENT_TIMESTAMP 추가
        @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")),

        // BaseTimeEntity의 updatedAt -> ON UPDATE CURRENT_TIMESTAMP 추가
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"))
})
public class CounselorAbsence extends BaseTimeEntity {

    // [1] FK용 컬럼 직접 매핑 (CamelCase 변수명)
    @Column(name = "counselor_id", nullable = false)
    private Long counselorId;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = true) // NULL 가능 (무기한)
    private LocalDateTime endAt;

    @Column(name = "reason", nullable = false, length = 200)
    private String reason;

    @Column(name = "created_by", nullable = true) // NULL 가능 (ON DELETE SET NULL)
    private Long createdBy;

    // [2] 연관관계 매핑 (읽기 전용)
    // 상담사 프로필 (삭제 시 Cascade)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "counselor_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_absence_counselor")
    )
    private CounselorProfile counselorProfile;

    // 생성자 (삭제 시 Set Null)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "created_by",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_absence_created_by")
    )
    private User createdByUser;

}