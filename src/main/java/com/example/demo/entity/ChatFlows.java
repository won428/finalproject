package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "parent")
@Entity
@Table(
        name = "chat_flows",
        indexes = {
                @Index(name = "idx_parent_id", columnList = "parent_id")
        }
)
/*  */
public class ChatFlows extends BasePkEntity {
//    변수명	                                내용	                                                     규격	        제약조건
//    id		                                                                                    BIGINT	        PK, AUTO_INCREMENT

//    parent_id	                상위노드ID                                                            BIGINT	        NULL
//                              (NULL이면 최상위 루트)

//    node_type	                노드 타입
//                              MENU: 하위 버튼이 더 존재함 (계속 선택).
//                              LEAF (또는 ANSWER): 더 이상 하위 버튼이 없고, 답변만 보여주고 종료.
//                              ACTION: 앱 내 특정 페이지로 이동하거나 API를 호출해야 하는 경우.	        VARCHAR(20)	    NOT NULL

//    button_text	            사용자가 누를 버튼의 텍스트	                                            VARCHAR(100)	NOT NULL

//    response_message	        버튼 클릭 시 봇이 출력할 멘트	                                        TEXT

//    action_code	            버튼 클릭했을 때 앱이 수행할 동작 식별코드                                 VARCHAR(50)	    NULL
//                              DEEP_LINK: 특정 URL로 이동
//                              OPEN_MODAL: 팝업창 띄우기
//                              등등

//    display_order	            같은 노드의 버튼이 항상 같은 순서로 정렬되어 나오도록 생성한 정렬용 컬럼       INT	            DEFAULT 0
//                              ex : 10,20,30

//    is_active	                노출여부                                                             BOOLEAN	        DEFAULT TRUE
//                              사용자가 사용하지 않는 버튼은 안보임 처리용


/*  [comments]
chat_flows 테이블 내에서 셀프 참조를 반복하는 재귀적 참조구조.
트리 구조로 하위 노드를 타고 내려가며 테이블을 여러개로 나눌 필요 없이 한 테이블로 기능 구현이 가능함.


셀프 참조(부모 포인터) 자체는 이미 구현됨

**삭제 연쇄(CASCADE)**는 DDL/Flyway에서 적용해야 동일 동작

필요하면 children 컬렉션을 추가하면 트리 탐색이 더 편해짐 (선택)
*/



    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "parent_id",
            nullable = true,
            foreignKey = @ForeignKey(name = "fk_chat_flows_parent")
    )
    private ChatFlows parent;

    @Column(name = "node_type", nullable = false, length = 20)
    private String nodeType;

    @Column(name = "button_text", nullable = false, length = 100)
    private String buttonText;

    @Lob
    @Column(name = "response_message")
    private String responseMessage;

    @Column(name = "action_code", length = 50)
    private String actionCode;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;

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
}
