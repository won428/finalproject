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
public class BoardStatus {
//      변수명	               내용	                                            규격	                    제약조건
//    board_id		                                                            BIGINT 	            PK, FK, NOT NULL

//    status_code	    게시글 상태
//                      RECRUITING, RECRUIT_DONE, UNANSWERED, ANSWERED, NONE	VARCHAR(30)	        PK NOT NULL

//    label	            게시글 한글라벨                                            VARCHAR(30)         NOT NULL
//                      "모집중", "모집완료", "미답변", "답변완료”

//    soft_order	    한 게시판 안에서 상태 필터 옵션 표시 순서를 정하는 값           INT	                NOT NULL
//                      - STUDY 게시판
//                      RECRUITING (모집중) → sort_order=1
//                      RECRUIT_DONE (모집완료) → sort_order=2
//                      -QNA 게시판
//                      UNANSWERED (미답변) → sort_order=1
//                      ANSWERED (답변완료) → sort_order=2
//                      - COUNSEL 게시판
//                      NONE (상태없음) → sort_order=1
//                      프론트에서 ORDER BY sort_order ASC로 정렬

//    is_filterable		                                                        TINYINT	            NOT NULL DEFAULT 1

//    is_default		                                                        TINYINT	            NOT NULL DEFAULT 0


/*  [comments]
ALTER TABLE board
ADD CONSTRAINT fk_board_default_status
FOREIGN KEY (board_id, default_status_code)
REFERENCES board_status(board_id, status_code);

= board 테이블의 default_status_code를 board_status 테이블에 FK로 묶기
*/
}
