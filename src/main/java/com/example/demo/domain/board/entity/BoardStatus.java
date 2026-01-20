package com.example.demo.domain.board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

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
public class BoardStatus {

    // [1] 복합키 ID - board_id
    @Id
    @Column(name = "board_id", nullable = false)
    private Long boardId;

    // [2] 복합키 ID - status_code
    @Id
    @Column(name = "status_code", nullable = false, length = 30)
    private String statusCode;

    @Column(name = "label", nullable = false, length = 30)
    private String label;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    // [3] TINYINT(1) + Default 1
    @Column(name = "is_filterable", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("1")
    private Boolean isFilterable = true;

    // [4] TINYINT(1) + Default 0
    @Column(name = "is_default", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("0")
    private Boolean isDefault = false;

    // [5] Generated Column (DB가 계산하는 컬럼)
    // insertable/updatable = false 필수
    @Column(
            name = "default_marker",
            insertable = false,
            updatable = false,
            columnDefinition = "VARCHAR(30) GENERATED ALWAYS AS (CASE WHEN is_default = 1 THEN status_code ELSE NULL END) STORED"
    )
    private String defaultMarker;

    // [6] 연관관계 매핑 (FK 제약조건 이름 및 OnDelete Cascade)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "board_id",
            insertable = false, // @Id boardId가 관리
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_board_status_board")
    )
    @OnDelete(action = OnDeleteAction.CASCADE) // DDL에 ON DELETE CASCADE 추가
    private Board board;

    // [7] 식별자 클래스 (Lombok 적용)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class BoardStatusId implements Serializable {
        private Long boardId;      // BoardStatus의 변수명과 일치
        private String statusCode; // BoardStatus의 변수명과 일치
    }
}