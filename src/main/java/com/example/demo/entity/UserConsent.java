package com.example.demo.entity;

import com.example.demo.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user", "policy"})
@Entity
@Table(
        name = "user_consent",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_policy_history", columnNames = {"user_id", "policy_id"})
        }
)
/* 개인정보 수집 동의/거부 */
public class UserConsent extends BaseTimeEntity {
//    변수명	내용	규격	제약조건
//    id		                                                BIGINT	        PK
//    user_id	    유저 ID	                                    BIGINT	        FK
//    policy_id	    약관 버전, 본문 관리	                        BIGINT	        UNIQUE, FK
//    consent_type	약관 타입(개인정보, sms 마케팅정보 수신 동의)	    VARCHAR(50)	    UNIQUE, NOT NULL
//    status	    상태                                         TINYINT(1)	    NOT NULL
//                  동의 = 1 / 거부,미동의 = 0
//    given_at	    동의 시작일	                                DATETIME	    NULL
//    revoked_at	약관 철회일	                                DATETIME	    NULL
//    created_at	해당 행이 처음 만들어진 시각	                DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	해당 행이 마지막으로 수정된 시각	                DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "consent_type", nullable = false, length = 50)
    private String consent_type;

    @Column(name = "policy_id", nullable = false)
    private Long policy_id;

    // TINYINT(1) NOT NULL  (동의 여부)
    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "given_at")
    private LocalDateTime given_at;

    @Column(name = "revoked_at")
    private LocalDateTime revoked_at;

    // --- 연관관계 (단방향, 읽기 전용 매핑) ---

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_user_consent_user")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "policy_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_user_consent_policy")
    )
    private Policy policy;

    @PrePersist
    void prePersist() {
        // ddl-auto=create에서 Boolean이 NULL로 들어가 NOT NULL 깨지는 케이스 방지
        // @Column(name = "status", nullable = false)
        // private Boolean status = Boolean.FALSE;랑 맥락상 비슷
        if (status == null) status = Boolean.FALSE;
    }

/*  [comments]
유저의 약관 동의 이력 관리용 테이블
policy_id를 참조해 약관(policy table)의 본문, 버전, 시행일 등을 관리함
 */
}
