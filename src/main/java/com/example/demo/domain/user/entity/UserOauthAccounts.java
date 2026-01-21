package com.example.demo.domain.user.entity;

import com.example.demo.base.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user"}) // 순환참조/지연로딩 출력 방지
@Entity
@Table(
        name = "user_oauth_accounts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_oauth_provider_id",
                        columnNames = {"provider", "provider_user_id"}
                )
        },
        indexes = {
                @Index(name = "idx_oauth_user_id", columnList = "user_id")
        }
)
/* 소셜 로그인 테이블 */
public class UserOauthAccounts extends BaseTimeEntity {

    // user_id BIGINT NOT NULL, FK -> users(id) ON DELETE CASCADE
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    // users 엔티티 연관관계 (같은 user_id 컬럼을 사용하므로 읽기 전용으로 매핑, setter로 set 안됨.)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_oauth_user")
    )
    private User user;

    // provider VARCHAR(20) NOT NULL
    @Column(name = "provider", nullable = false, length = 20)
    private String provider;

    // provider_user_id VARCHAR(255) NOT NULL
    @Column(name = "provider_user_id", nullable = false, length = 255)
    private String provider_user_id;

    // provider_email VARCHAR(100) NULL
    @Column(name = "provider_email", length = 100)
    private String provider_email;

    // provider_email_verified BOOLEAN NOT NULL DEFAULT FALSE
    @Column(name = "provider_email_verified", nullable = false)
    private Boolean provider_email_verified;

    // linked_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    @Column(name = "linked_at", nullable = false)
    private LocalDateTime linked_at;

    @PrePersist
    void prePersist() {
        // ddl-auto=create에서 NULL로 들어가 NOT NULL 깨지는 케이스 방지
        if (provider_email_verified == null) provider_email_verified = Boolean.FALSE;
        if (linked_at == null) linked_at = LocalDateTime.now();
    }

}