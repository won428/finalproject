package com.example.demo.domain.auth.entity;

import com.example.demo.base.entity.BasePkEntity;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user", "role"})
@Entity
@Table(
        name = "user_roles",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_user_role_mapping", columnNames = {"user_id", "role_id"})
        },
        indexes = {
                @Index(name = "idx_user_roles_user_id", columnList = "user_id"),
                @Index(name = "idx_user_roles_role_id", columnList = "role_id")
        }
)
/* 유저-역할 매핑테이블 */
public class UserRoles extends BasePkEntity {
//    변수명	            내용	        규격	            제약조건
//    user_id	        유저 ID	    BIGINT	        PK, FK,  NOT NULL
//    role_id	        역할 ID	    BIGINT	        PK, FK, NOT NULL
//    granted_at	    행 생성일	DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP

    // user_id BIGINT NOT NULL
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    // role_id BIGINT NOT NULL
    @Column(name = "role_id", nullable = false)
    private Long role_id;

    // granted_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    @Column(name = "granted_at", nullable = false)
    private LocalDateTime granted_at;

    // FK(user_id) -> users(id) ON DELETE CASCADE
    // user_id 컬럼을 쓰기 주인으로 두고, 연관관계는 읽기 전용으로 둠
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_mapping_user")
    )
    private User user;

    // FK(role_id) -> roles(id) ON DELETE CASCADE
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "role_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_mapping_role")
    )
    private Roles role;

    @PrePersist
    void prePersist() {
        // ddl-auto=create 시 NULL로 들어가 NOT NULL 깨지는 케이스 방지
        if (granted_at == null) {
            granted_at = LocalDateTime.now();
        }
    }

/*  [comments]
하나의 계정에 두개 이상 역할(권한)이 들어갈 수 있으므로 교차 테이블 생성.
 */
}
