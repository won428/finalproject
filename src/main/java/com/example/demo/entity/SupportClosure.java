package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.*;
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
/*  */
public class SupportClosure extends BasePkEntity {

// 변수명	                내용	                규격	            제약조건
//id	                    고유ID	                BIGINT	            PK, AUTO_INCREMENT
//admin_id	                휴무를 설정한 운영자ID	BIGINT	            FK, NULL
//start_at	                휴무 시작일	            DATETIME	        NOT NULL
//end_at	                휴무 종료일	            DATETIME	        NOT NULL
//reason	                휴무사유                VARCHAR(100)	        NOT NULL
//                      설날 / 추석 / 서버점검 / 임시휴무 등
//created_at	            행 생성일	            DATETIME	        NOT NULL DEFAULT CURRENT_TIMESTAMP

    /*
    부재 체크 순서:
    • 채팅을 연결할 때: support_closure(공휴일) 체크 -> counselor_absence(개인 휴가) 체크 -> counselor_profile(is_active, max_chats) 체크 순으로 필터링

    서비스에서 검증할 것:
    • 업데이트할 때 last_read_message_id가 해당 session_id에 속하는지 확인하고 저장
    */

    @Column(name = "start_at", nullable = false)
    private LocalDateTime start_at;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime end_at;

    @Column(name = "reason", nullable = false, length = 100)
    private String reason;

    @Column(name = "admin_id")
    private Long admin_id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "admin_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_closure_admin")
    )
    private User admin;

}
