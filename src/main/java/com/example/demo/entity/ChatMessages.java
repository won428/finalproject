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
public class ChatMessages {
//    변수명	            내용	                                                규격	            제약조건

//    id	            고유ID	                                            BIGINT	        PK, AUTO_INCREMENT
//    session_id	    세션ID	                                            BIGINT	        FK, NOT NULL
//    sender_type	    메시지 전송자
//                      USER/COUNSELOR/SYSTEM	                            VARCHAR(20)	    NOT NULL

//    sender_user_id	USER/COUNSELOR일 때 users.id, SYSTEM이면 NULL	    BIGINT	        FK, NULL
//    message_type	    TEXT/SYSTEM/IMAGE/FILE	                            VARCHAR(20)	    NOT NULL DEFAULT 'TEXT’
//    content	        메시지 내용	                                        TEXT	        NOT NULL
//    created_at	    행 생성일	                                        DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP


/*  [DDL]
CREATE TABLE chat_message (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	session_id BIGINT NOT NULL,
	sender_type VARCHAR(20) NOT NULL,  -- USER/COUNSELOR/SYSTEM
	sender_user_id BIGINT NULL,        -- USER/COUNSELOR일 때 [users.id](http://users.id/), SYSTEM이면 NULL

	message_type VARCHAR(20) NOT NULL DEFAULT 'TEXT', -- TEXT/SYSTEM/IMAGE/FILE
	content TEXT NOT NULL,

	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

	KEY idx_msg_session_created (session_id, created_at),

	CONSTRAINT fk_msg_session
		FOREIGN KEY (session_id) REFERENCES chat_session(id),
	CONSTRAINT fk_msg_sender_user
		FOREIGN KEY (sender_user_id) REFERENCES users(id)
) ENGINE=InnoDB;
*/
}
