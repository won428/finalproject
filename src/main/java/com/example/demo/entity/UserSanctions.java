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
/* 유저 제한 상태(현재 상태만 저장, 제한 해제되면 삭제) */
public class UserSanctions {
//    변수명	            내용              	규격	            제약조건
//    id	            고유 ID	            BIGINT	            PK
//    user_id	        제한 유저	        BIGINT	            FK, NOT NULL
//    admin_id	        제재를 가한 관리자ID	BIGINT	            FK, NOT NULL
// sanction_type	제재 타입               VARCHAR(20)	        NOT NULL
// 'BAN_7_DAYS, BAN_PERMANENT, MUTE 등’
//reason	            제재 사유	        TEXT	           NULL
//started_at	        제재 시작일	        DATETIME	        NOT NULL DEFAULT CURRENT_TIMESTAMP
//expired_at	        제재 종료일	        DATETIME	        NOT NULL
//released_at	    관리자가 수동으로 중도해제 했을 경우 그 시각	DATETIME	NULL
}
