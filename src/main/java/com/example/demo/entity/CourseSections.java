package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "course")
@Entity
@Table(
        name = "course_sections",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_sort",
                        columnNames = {"course_id", "sort_order"}
                )
        }
)
public class CourseSections extends BasePkEntity {
//    변수명	            내용	            규격	        제약조건
//    id	            테이블 PK	    BIGINT	    PK
//    course_id	        강의 ID	        BIGINT	    FK, NOT NULL
//    title	            챕터 이름	    VARCHAR 	NOT NULL
//    sort_order	    챕터 회차	    INT	    NOT NULL
//    description	    설명	            VARCHAR	    NOT NULL

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false, foreignKey = @ForeignKey(name = "fk_course_section_course"))
    private Course course;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;
}
