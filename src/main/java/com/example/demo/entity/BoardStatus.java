package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"board"})
@Entity
@Table(
        name = "board_status",
        indexes = {
                @Index(name = "idx_board_status_board_sort", columnList = "board_id, sort_order")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_board_status_one_default", columnNames = {"board_id", "default_marker"})
        }
)
@IdClass(BoardStatus.BoardStatusId.class)
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

/* “board의 기본 status”는 BoardStatus에서 is_default=1인 row를 조회해서 가져오는 방식 */

    @Id
    @Column(name = "board_id", nullable = false)
    private Long board_id;

    @Id
    @Column(name = "status_code", nullable = false, length = 30)
    private String status_code;

    @Column(name = "label", nullable = false, length = 30)
    private String label;

    @Column(name = "sort_order", nullable = false)
    private Integer sort_order;

    @Column(name = "is_filterable", nullable = false)
    private Boolean is_filterable = Boolean.TRUE;

    @Column(name = "is_default", nullable = false)
    private Boolean is_default = Boolean.FALSE;

    // GENERATED ALWAYS ... STORED (읽기 전용)
    @Column(name = "default_marker", insertable = false, updatable = false, length = 30)
    private String default_marker;

    // 읽기 전용 연관관계
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "board_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_board_status_board")
    )
    private Board board;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BoardStatusId implements Serializable {
        private Long board_id;
        private String status_code;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BoardStatusId)) return false;
            BoardStatusId that = (BoardStatusId) o;
            return Objects.equals(board_id, that.board_id)
                    && Objects.equals(status_code, that.status_code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(board_id, status_code);
        }
    }

}
