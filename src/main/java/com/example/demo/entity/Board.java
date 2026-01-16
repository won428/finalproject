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
public class Board {
//    변수명	                             내용	                                                                규격	                제약조건

//    board_id		         	                                                                                BIGINT             PK AUTO_INCREMENT

//    board_code	        게시판 코드
//                          STUDY, COUNSEL, RECRUIT, QNA	                                                    VARCHAR(30)	    NOT NULL UNIQUE

//    board_name	        게시판 이름                                                                           VARCHAR(50)	    NOT NULL
//                          스터디, 상담, 팀모집, 질문&답변
//
//    is_active	            상태가 1인 게시판만 목록에 노춞                                                          TINYINT	        NOT NULL DEFAULT 1
//                          0이면 점검중이거나 폐쇄

//    default_status_code	게시판이 게시글 생성시 내려주는 기본 상태코드.
//                          게시글이 처음 생성될 때 프론트에서 post.status_code가 NULL로 넘어오면
//                          R ECRUITING, UNANSWERED, NONE이 기본값이 되어 게시판의 성격에 맞게 기본값을 설정하게 함.
//                          post 테이블에 행이 INSERT 되기 전에 값을 채워서 항상 NOT NULL로 저장되야 함.	                VARCHAR(30)	    NOT NULL
}
