package com.example.demo.domain.chat.entity;

import com.example.demo.base.enums.NodeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "chat_flows",
        indexes = {
                @Index(name = "idx_parent_order", columnList = "parent_id, display_order")
        }
)
public class ChatFlows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // [1] 트리 구조 부모
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_chat_flows_parent"))
    @OnDelete(action = OnDeleteAction.CASCADE) // DDL: ON DELETE CASCADE 반영
    private ChatFlows parent;

    // [2] 흐름 제어 (Next Node)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_node_id", foreignKey = @ForeignKey(name = "fk_chat_flows_next_node"))
    @OnDelete(action = OnDeleteAction.SET_NULL) // DDL: ON DELETE SET NULL 반영
    private ChatFlows nextNode;

    // [3] 노드 성격
    @Enumerated(EnumType.STRING)
    @Column(name = "node_type", length = 20, nullable = false)
    private NodeType nodeType;

    // [4] 봇 발화 및 UI
    @Column(name = "response_message", columnDefinition = "TEXT", nullable = false)
    private String responseMessage;

    @Column(name = "button_text", length = 100)
    private String buttonText;

    // [5] 기능 연동
    @Column(name = "action_code", length = 50)
    private String actionCode;

    // Default 설정 (Java 초기화 + DB DDL 반영)
    @Column(name = "display_order")
    @ColumnDefault("0")
    private Integer displayOrder = 0;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("TRUE")
    private Boolean isActive = true;

    // 편의상 생성자 (필요 시 추가)
    public ChatFlows(NodeType nodeType, String responseMessage) {
        this.nodeType = nodeType;
        this.responseMessage = responseMessage;
    }
}
    /*  [DDL]
CREATE TABLE chat_flows (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	parent_id BIGINT NULL,                 -- 상위 노드 ID (NULL이면 최상위 루트 메뉴)
	node_type VARCHAR(20) NOT NULL,        -- 노드 타입 (MENU, ANSWER, ACTION 등)
	button_text VARCHAR(100) NOT NULL,     -- 사용자가 누를 버튼의 텍스트
	response_message TEXT,                 -- 버튼 클릭 시 봇이 출력할 멘트
	action_code VARCHAR(50) NULL,          -- 앱 기능 연동용 코드 (예: OPEN_PAGE_A)
	display_order INT DEFAULT 0,           -- 버튼 노출 순서
	is_active BOOLEAN DEFAULT TRUE,        -- 노출 여부

	FOREIGN KEY (parent_id) REFERENCES chat_flows(id) ON DELETE CASCADE,
	INDEX idx_parent_id (parent_id)        -- 조회 성능을 위한 필수 인덱스
);
*/

