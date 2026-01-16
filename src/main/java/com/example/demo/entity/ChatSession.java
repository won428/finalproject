package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "")
/*  */
public class ChatSession {

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
5. “open_key”는 값을 직접 넣는게 아니라, staus의 상태에 따라 DB가 알아서 계산 후 저장하는 생성컬럼임.
    open_key에 값이 있으면 INSERT가 실패하게 되는데 스프링에서 이 실패를 잡은 후 “이미 진행중인 상담이 있습니다.”라고 응답하고, 기존 열린 세션으로 리다이렉트하면 되는 것.
*/




/*  [DDL]
CREATE TABLE chat_session (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	user_id BIGINT NOT NULL,
	counselor_id BIGINT NULL,   -- 배정 전에는 NULL

	status VARCHAR(20) NOT NULL,      -- QUEUED/CONNECTING/ACTIVE/UNAVAILABLE/CLOSED
	close_reason VARCHAR(40) NULL,    -- OUT_OF_HOURS/GLOBAL_CLOSED/NO_COUNSELOR/COUNSELOR_ABSENT/TIMEOUT/USER_ENDED/COUNSELOR_ENDED 등
	ended_by VARCHAR(20) NULL,        -- USER/COUNSELOR/SYSTEM

	queued_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	assigned_at DATETIME NULL,
	started_at DATETIME NULL,
	ended_at DATETIME NULL,

	-- “열린 세션”을 유저당 1개만 허용하고 싶으면(권장)
	open_key TINYINT GENERATED ALWAYS AS (
		CASE
			WHEN status IN ('QUEUED','CONNECTING','ACTIVE') THEN 1
			ELSE NULL
		END
	) STORED,

	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

	KEY idx_session_user_created (user_id, created_at),
	KEY idx_session_counselor_created (counselor_id, created_at),
	KEY idx_session_status (status),
	UNIQUE KEY uk_user_open (user_id, open_key),

	CONSTRAINT fk_session_user
		FOREIGN KEY (user_id) REFERENCES users(id),
	CONSTRAINT fk_session_counselor
		FOREIGN KEY (counselor_id) REFERENCES users(id)
) ENGINE=InnoDB;
*/
}
