package com.example.demo.domain.tag.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
@Table(
        name = "tag",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tag_tag_key", columnNames = {"tag_key"})
        }
)
/* 게시글 태그 */
@AttributeOverride(name = "id", column = @Column(name = "tag_id"))
public class Tag {
//    변수명	        내용	                                 규격	            제약조건
//    tag_id	    태그ID	                             BIGINT	            PK, AUTO_INCREMENT
//    name	        사용자가 볼 태그명                      VARCHAR(50)	    NOT NULL
//                  ex) MySQL
//    tag_key	    서버가 정규화한 검색/중복 방지용 키워드     VARCHAR(60)	    NOT NULL UNIQUE
//                  ex) mysql
//    created_at	생성일	                             DATETIME	        NOT NULL DEFAULT  CURRENT_TIMESTAMP

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    // name VARCHAR(50) NOT NULL
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    // tag_key VARCHAR(60) NOT NULL UNIQUE
    // unique=true는 @Table의 uniqueConstraints로 대체되었으므로 여기선 생략 가능
    @Column(name = "tag_key", nullable = false, length = 60)
    private String tagKey;

    // created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    @CreationTimestamp // INSERT 시 Hibernate가 시간 자동 주입
    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP") // DDL 생성 시 DEFAULT 문구 추가
    private LocalDateTime createdAt;

/*  [comments]
post 테이블에 tag_id가 참조되어있지 않기 때문에 강제성 없음.
게시글 작성 시 태그 사용할 유저는 사용하고, 아닌 유저는 사용 안하는 수준의 자유도.
인스턴스 수가 많은 태그의 ID를 “요즘 뜨는 카테고리”로 추천하는 기능도 구성 가능함.

사용자가 게시글에 태그를 붙일 때
서버에서 입력값을 정규화(lowercase, 문자간 공백 제거)해서 tag_key로 가져옴
→ tag 테이블에 이미 존재하는 값이면 tag_id를 쓰고 없는 값이면 tag 테이블에 INSERT.
*/




/*  [DDL]
CREATE TABLE tag (
tag_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
name        VARCHAR(50) NOT NULL,        -- 사용자에게 보여줄 이름(표시용)
tag_key     VARCHAR(60) NOT NULL UNIQUE, -- 정규화된 키(유니크는 여기)
created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
*/

}
