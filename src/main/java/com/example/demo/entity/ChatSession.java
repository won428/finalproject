package com.example.demo.entity;

import com.example.demo.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user", "counselor_profile"})
@Entity
@Table(
        name = "chat_session",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_session_open_key", columnNames = {"open_key"})
        },
        indexes = {
                @Index(name = "idx_session_user_created", columnList = "user_id, created_at"),
                @Index(name = "idx_session_counselor_created", columnList = "counselor_id, created_at"),
                @Index(name = "idx_session_status", columnList = "status"),

                // WAITING 큐 조회 최적화
                @Index(name = "idx_session_status_queued", columnList = "status, queued_at"),

                // 상담사별 상태 조회 최적화
                @Index(name = "idx_session_counselor_status", columnList = "counselor_id, status, created_at")
        }
)
/*  */
public class ChatSession extends BaseTimeEntity {
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "counselor_id")
    private Long counselor_id;

    @Column(name = "open_key", nullable = false, length = 36, unique = true)
    private String open_key;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "close_reason", length = 50)
    private String close_reason;

    @Column(name = "ended_by", length = 20)
    private String ended_by;

    @Column(name = "queued_at", nullable = false)
    private LocalDateTime queued_at;

    @Column(name = "assigned_at")
    private LocalDateTime assigned_at;

    @Column(name = "started_at")
    private LocalDateTime started_at;

    @Column(name = "ended_at")
    private LocalDateTime ended_at;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_session_user")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "counselor_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_session_counselor")
    )
    private CounselorProfile counselor_profile;

    @PrePersist
    void prePersist() {
        if (status == null) status = "WAITING";
        if (queued_at == null) queued_at = LocalDateTime.now();
        if (open_key == null) open_key = UUID.randomUUID().toString();
    }

//    변수명	            내용	                                                        규격	                                            제약조건
//    id	            채팅방 ID	                                                BIGINT	                                        PK, AUTO_INCREMENT
//    user_id	        사용자의 ID	                                                BIGINT	                                        FK, NOT NULL
//    counselor_id	    상담사의 ID	                                                BIGINT	                                        FK, NULL

//    status	        채팅 상태                                                    VARCHAR(20)	                                    NOT NULL
//                      QUEUED(요청 접수됨)/
//                      CONNECTING(상담사 배정은 됐지만 아직 연결 전)/
//                      ACTIVE(상담 진행 중)/
//                      UNAVAILABLE(상담시간이 아니거나,휴무거나, 상담사 부재)/
//                      CLOSED(상담 종료 확정)

//    close_reason	    채팅 종료 사유                                                VARCHAR(40)	                                    NULL
//                      OUT_OF_HOURS(운영시간 아님)/
//                      GLOBAL_CLOSED(공휴일, 임시휴무)/
//                      NO_COUNSELOR(배정 가능한 상담사 없음)/
//                      TIMEOUT(일정시간 수락이 없어 시스템이 종료)/
//                      USER_ENDED(사용자가 종료)/
//                      COUNSELOR_ENDED(상담사가 종료)

//    ended_by	        누가 종료했는지                                                VARCHAR(20)	                                    NULL
//                      USER/COUNSELOR/SYSTEM

//    queued_by	        요청 접수 시각이 컬럼을 기준으로 타임아웃 기준점을 잡음.	            DATETIME	                                    NOT NULL DEFAULT CURRENT_TIMESTAMP
//
//    assigned_at	    요청 수락한 시각	                                            DATETIME	                                    NULL
//    started_at	    상담이 시작된 시각	                                            DATETIME	                                    NULL
//    ended_at	        상담 종료된 시각	                                            DATETIME	                                    NULL

//    [생성컬럼]open_key	열린 세션을 유저당 1개만 허용하는 생성컬럼	                        TINYINT GENERATED ALWAYS AS (                   status의 상태가 'QUEUED','CONNECTING','ACTIVE’ 중 하나라도 해당되면 해당 컬럼에 1이 채워지고,
//                                                                                  CASE WHEN status IN                             해당되지 않으면 NULL이 됨. = 일반컬럼처럼 값을 직접 넣는게 아니라 status의 상태에 따라 DB가 알아서 저장함.
//                                                                                  ('QUEUED','CONNECTING','ACTIVE') THEN 1
//                                                                                   ELSE NULL END) STORED

//    created_at	    행 생성된 시각	                                            DATETIME	                                    NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	    행 수정된 시각	                                            DATETIME	                                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//


/*  [comments]
1. 상담사/사용자 양쪽이 대화목록(이력)을 조회할 수 있음.
2. 메시지 확인이 필요할 경우 chat_session.id로 chat_mssage를 참조해서 메시지 확인.
3. 채팅 메시지를 chat_session 테이블로 하나의 대화에 귀속시켜서 정렬/검색/페이징 기능이 가능해짐.
4. counselor_id는 배정 전까지 NULL이었다가 배정 후 채워짐.
5. 적용 가이드
• 중복 세션 방지: 이전 DDL에 있던 Generated Column을 제거했으므로, "한 유저가 동시에 두 개의 상담을 못 하게 하는 로직"은 **애플리케이션(Service Layer)**에서 status가 'ACTIVE'인 방이 있는지 체크하는 방식으로 처리해야 함. (UUID 사용을 위해 DB 복잡도를 낮춤)
• open_key 생성: Java에서 UUID.randomUUID().toString()으로 생성해서 Insert 하면 됨.
*/




/*  [DDL]
CREATE TABLE chat_session (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- 1. 참여자 정보
    user_id         BIGINT NOT NULL,
    counselor_id    BIGINT NULL,   -- 대기 중일 때는 NULL, 배정 후 상담사 ID

    -- 2. 세션 식별 및 보안
    open_key        VARCHAR(36) NOT NULL, -- 외부 공개용 UUID (API 호출용)

    -- 3. 상태 및 종료 사유
    status          VARCHAR(20) NOT NULL DEFAULT 'WAITING', -- WAITING, ACTIVE, CLOSED 등
    close_reason    VARCHAR(50) NULL,  -- USER_LEFT, COUNSELOR_LEFT, TIME_OUT 등
    ended_by        VARCHAR(20) NULL,  -- SYSTEM, USER, COUNSELOR

    -- 4. 시간 기록 (운영 지표용)
    queued_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 대기열 진입 시간
    assigned_at     DATETIME NULL, -- 상담사 배정 시간
    started_at      DATETIME NULL, -- 실제 대화 시작 시간
    ended_at        DATETIME NULL, -- 종료 시간

    -- 5. 시스템 메타데이터
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- [제약 조건 및 인덱스]

    -- 1) 외부 공개 키는 유일해야 함
    CONSTRAINT uk_session_open_key UNIQUE (open_key),

    CONSTRAINT fk_session_user
        FOREIGN KEY (user_id) REFERENCES users (id),

    -- [핵심] 상담사는 반드시 프로필이 있는 유저여야 함
    CONSTRAINT fk_session_counselor
        FOREIGN KEY (counselor_id) REFERENCES counselor_profile (counselor_id),

    INDEX idx_session_user_created (user_id, created_at),
    INDEX idx_session_counselor_created (counselor_id, created_at),
    INDEX idx_session_status (status),
    INDEX idx_session_status_queued (status, queued_at),
    INDEX idx_session_counselor_status (counselor_id, status, created_at)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
*/
}
