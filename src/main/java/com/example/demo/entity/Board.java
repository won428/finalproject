package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
@Table(
        name = "board",
        uniqueConstraints = {
                // [API 성능 최적화] board_code에 유니크 인덱스가 걸리므로
                // /api/boards/{boardCode} 조회 시 매우 빠름 (Index Scan)
                @UniqueConstraint(name = "uk_board_board_code", columnNames = {"board_code"})
        }
)
// [핵심] 부모의 "id" 컬럼 매핑을 "board_id"로 재정의
@AttributeOverride(name = "id", column = @Column(name = "board_id"))
public class Board extends BasePkEntity {

    // [삭제됨] private Long board_id; -> BasePkEntity의 id 사용

    @Column(name = "board_code", nullable = false, length = 30)
    private String boardCode; // Java: CamelCase, DB: snake_case

    @Column(name = "board_name", nullable = false, length = 50)
    private String boardName;

    // [수정] TINYINT(1) + Default 1
    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("1")
    private Boolean isActive = true;

}
