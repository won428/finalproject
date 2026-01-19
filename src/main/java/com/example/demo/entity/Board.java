package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
@Table(
        name = "board",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_board_board_code", columnNames = {"board_code"})
        }
)
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long board_id;

    @Column(name = "board_code", nullable = false, length = 30)
    private String board_code;

    @Column(name = "board_name", nullable = false, length = 50)
    private String board_name;

    @Column(name = "is_active", nullable = false)
    private Boolean is_active = Boolean.TRUE;

}
