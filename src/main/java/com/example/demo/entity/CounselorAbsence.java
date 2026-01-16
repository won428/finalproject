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
public class CounselorAbsence {
//    변수명	            내용	                                    규격	                제약조건
//    id		                                                BIGINT	            PK AUTO_INCREMENT
//    counselor_id	    상태 변경된 상담사 ID(users.id)	        BIGINT	            FK, NOT NULL
//    start_at	        부재 시작일	                            DATETIME	        NOT NULL
//    end_at	        부재 종료일	                            DATETIME	        NULL
//    reason	        사유(연차/ 병가 등)	                    VARCHAR(200)	    NOT NULL
//    created_by	    상담사의 상태를 변경한 관리자 ID(users.id)	BIGINT	            FK, NULL
//    created_at	    행이 생성된 날짜	                        DATETIME	        NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	    행이 수정된 날짜	                        DATETIME	        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


/*  [DDL]
CREATE TABLE counselor_absence (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	counselor_id BIGINT NOT NULL,
	start_at DATETIME NOT NULL,
	end_at DATETIME NULL,           -- NULL이면 “해제할 때까지 무기한 부재”도 가능
	reason VARCHAR(200) NOT NULL,   -- 사유(연차/교육/병가/기타)
	created_by BIGINT NULL,         -- HR 담당자 [users.id](http://users.id/) (선택)

	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

	KEY idx_absence_counselor_range (counselor_id, start_at, end_at),

	CONSTRAINT fk_absence_counselor
		FOREIGN KEY (counselor_id) REFERENCES users(id),
	CONSTRAINT fk_absence_created_by
		FOREIGN KEY (created_by) REFERENCES users(id)
) ENGINE=InnoDB;
 */
}
