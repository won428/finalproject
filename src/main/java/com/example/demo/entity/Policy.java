package com.example.demo.entity;

import com.example.demo.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
@Table(
        name = "policy",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_policy", columnNames = {"type", "version", "locale"})
        },
        indexes = {
                @Index(name = "idx_policy_active", columnList = "type,status,effective_at")
        }
)
/* 약관 본문, 버전, 시행 관리 */
public class Policy extends BaseTimeEntity {
//    변수명	        내용                                  	규격	            제약조건
//    id	        테이블의 PK	                            BIGINT	        PK
//    type	        약관 종류	                            VARCHAR(50) 	UNIQUE, NOT NULL
//    version	    개정 버전	                            LONGTEXT	    UNIQUE, NOT NULL
//    content	    약관본문	                                DATETIME	    NOT NULL
//    published_at	발행 시점(시행일 아님)	                    DATETIME	    NOT NULL
//    effective_at	효력을 갖는 날짜(시행일)	                DATETIME	    NULL
//    created_at	해당 행이 언제 생겼는지	                                    DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	언제 개정됐는지(덮어씌우기 X, 날짜만 변경    	DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
//    status	    초안/발행/ 폐기 등 상태                     VARCHAR(20)	NOT NULL DEFAULT 'DRAFT’
//                  DRAFT / PUBLISHED / RETIRED

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "version", nullable = false, length = 50)
    private String version;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "effective_at", nullable = false)
    private LocalDateTime effective_at;

    // DRAFT / PUBLISHED / RETIRED
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "published_at")
    private LocalDateTime published_at;

    @Column(name = "locale", nullable = false, length = 10)
    private String locale;

    @PrePersist
    void prePersist() {
        // ddl-auto=create 시 NULL 들어가 NOT NULL 깨지는 케이스 방지 + DDL default와 동일하게 맞춤
        if (status == null) status = "DRAFT";
        if (locale == null) locale = "ko-KR";
    }

/*  [comments]
유저가 동의 / 거부/ 미동의할 약관들의 버전과 내용, 개정일, 시행일 등을 관리할 테이블
 */

}
