package com.example.demo.entity;

import com.example.demo.entity.base.BasePostEntity;
import com.example.demo.enums.LanguageCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString(exclude = {"user"})
@Entity
@Table(name = "mentoring_posts")
/* 멘토링 글 목록 */
public class MentoringPost extends BasePostEntity {
//    변수명	                        내용	            규격	            제약조건
//    id	                        테이블 PK	    BIGINT	        PK
//    tags	                        태그	            BIGINT	        FK
//    mentor_id	                    멘토 ID	        BIGINT  	    FK NOT NULL
//    program_start_date	        멘토링 진행 시작	DATE	        NOT NULL
//    program_end_date              멘토링 종료 날짜   DATE            NOT NULL
//    session_count	                멘토링 진행 횟수	INT	            NOT NULL
//    title	                        제목	            VARCHAR	        NOT NULL
//    content	                    내용	            MEDIUMTEXT	    NOT NULL
//    capacity	                    모집 정원	    INT	            NOT NULL DEFAULT 1
//    recruitment_start_date	    모집 시작 날짜	DATE	    NOT NULL
//    recruitment_end_date	        모집 종료 날짜	DATE	    NOT NULL
//    created_at	                생성 날짜	    DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP
//    update_at                     수정 날짜         DATETIME
//    language_code	                멘토링 언어	    ENUM	        NOT NULL


    // FK 컬럼(쓰기용)
    @Column(name = "mentor_id", nullable = false)
    private Long mentorId;

    // 연관관계(읽기전용) - (필드명 user 그대로 유지)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "mentor_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_mentoring_post_user")
    )
    private User user;


    @Column(name = "program_start_date", nullable = false)
    private LocalDate programStartDate;

    @Column(name = "program_end_date", nullable = false)
    private LocalDate programEndDate;

    @Column(name = "session_count", nullable = false)
    private Integer sessionCount;

    @Column(name = "capacity", nullable = false)
    private Integer capacity = 1;

    @Column(name = "recruitment_start_date", nullable = false)
    private LocalDate recruitmentStartDate;

    @Column(name = "recruitment_end_date", nullable = false)
    private LocalDate recruitmentEndDate;

    @Column(name = "language_code", nullable = false)
    @Enumerated(EnumType.STRING)
    private LanguageCode languageCode;
}
