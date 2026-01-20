package com.example.demo.domain.mentoring.entity;

import com.example.demo.base.BaseTimeEntity;
import com.example.demo.domain.user.entity.User;
import com.example.demo.enums.CurrentStatus;
import com.example.demo.enums.MenteeDesiredField;
import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"user", "mentoringPost"})
@Entity
@Table(
        name = "mentee_applications",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_mentee_applications_mentoring_posts_user",
                        columnNames = {"mentoring_post_id", "user_id"}
                )
        },
        indexes = {
                @Index(name="idx_mentee_applications_mentoring_posts", columnList="mentoring_post_id"),
                @Index(name = "idx_mentee_applications_user", columnList = "user_id")
        }
)
/* 멘티 신청 목록 */
public class MenteeApplications extends BaseTimeEntity {

    // FK 컬럼(쓰기용) - 이제 NULL 허용
    @Column(name = "mentoring_post_id")
    private Long mentoringPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "mentoring_post_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_mentee_applications_mentoring_posts")
    )
    private MentoringPost mentoringPost;

    // user도 탈퇴해도 이력 남길거면 동일하게 nullable
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_mentee_applications_user")
    )
    private User user;

    // status ENUM NOT NULL DEFAULT PENDING
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.PENDING;

    // current_status ENUM NOT NULL
    @Enumerated(EnumType.STRING)
    @Column(name = "current_status", nullable = false, length = 20)
    private CurrentStatus currentStatus;

    // desired_field ENUM NOT NULL
    @Enumerated(EnumType.STRING)
    @Column(name = "desired_field", nullable = false, length = 20)
    private MenteeDesiredField desiredField;

    // goal VARCHAR NOT NULL
    @Column(name = "goal", nullable = false, length = 255)
    private String goal;

}
