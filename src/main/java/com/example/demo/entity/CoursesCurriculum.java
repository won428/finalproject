package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "courseSection")
@Entity
@Table(
        name = "courses_curriculum",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_curriculum_section_sort",
                        columnNames = {"course_section_id", "sort_order"}
                )
        }
)
/* 강의 커리큘럼 */
public class CoursesCurriculum extends BasePkEntity {
//    변수명	                내용	        규격	        제약조건
//    id	                테이블 PK	BIGINT	    PK
//    course_section_id 	강의 대분류 FK	    BIGINT	    FK
//    title	                차시 제목	VARCHAR	    NOT NULL
//    description	        차시 설명	VARCHAR	    NOT NULL
//    sort_order	        차시 순서	INT	    NOT NULL

    // FK 컬럼(쓰기용)
    @Column(name = "course_section_id", nullable = false)
    private Long courseSectionId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "course_section_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_curriculum_course_section")
    )
    private CourseSections courseSection;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;
}
