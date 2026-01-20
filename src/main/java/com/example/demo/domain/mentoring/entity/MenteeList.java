package com.example.demo.domain.mentoring.entity;

import com.example.demo.base.BasePkEntity;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDate;

@Getter
@Setter
@ToString(exclude = {"mentee", "mentoringPost"})
@Entity
@Table(name = "mentee_list")
/* 멘티 목록 */
public class MenteeList extends BasePkEntity {
//    변수명	                내용	                규격	        제약조건
//    id	                테이블 PK	        BIGINT	    PK
//    mentee_id	            멘티 ID	            BIGINT	    FK
//    mentoring_post_id	신청 멘토링 글 ID	    BIGINT	    FK
//    start_date	        멘토링 시작 날짜	    DATE	    NOT NULL
//    end_date	            멘토링 종료 날짜	    DATE        NOT NULL

    // mentee_id BIGINT FK
    @Column(name = "mentee_id", nullable = false)
    private Long menteeId;

    // 같은 mentee_id 컬럼을 사용, 조회용(read-only)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "mentee_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_mentee_list_mentee")
    )
    private User mentee;

    // mentoring_posts_id BIGINT FK
    @Column(name = "mentoring_post_id", nullable = false)
    private Long mentoringPostId;

    // 같은 mentoring_posts_id 컬럼을 사용, 조회용(read-only)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "mentoring_post_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_mentee_list_mentoring_post")
    )
    private MentoringPost mentoringPost;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;


    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}
