package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"counselor"})
@Entity
@Table(name = "counselor_profile")
/*  */
public class CounselorProfile {
    //    변수명	                내용	                                규격	            제약조건
//    counselor_id	        user.id	                                BIGINT	        PK, FK
//    max_concurrent_cahts	상담사가 받을 수 있는 채팅 세션의 최대 갯수	INT	NOT         NULL DEFAULT 3
//    is_active	            1 = 근무중 / 0 = 부재, 퇴사, 휴직 등의 상태	TINYINT	        NOT NULL DEFAULT 1
//    created_at	        해당 행이 생성된 시각	                    DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	        해당 행이 수정된 시각	                    DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP


/*  [comments]
1. HR이 “부재”로 상태를 변경할 때 기간과 사유를 저장해야 함.
2. is_active가 0이면 배정 제외.
3. 현재 일자가 counselor_absence.start_at ~ end_at 사이에 포함되면 배정 제외.
4. 위의 2개의 조건에 해당되지 않을 경우 “근무”
5. 존재하는 상담사만큼 행도 1:1로 생성(예: 상담사가 7명일 경우 7행)
*/

/*  [DDL]
CREATE TABLE counselor_profile (
	counselor_id BIGINT PRIMARY KEY,      -- [users.id](http://users.id/) (상담사 1명당 1행)
	max_concurrent_chats INT NOT NULL DEFAULT 3,
	is_active TINYINT(1) NOT NULL DEFAULT 1,  -- 장기/무기한 제외(퇴사/휴직/권한회수 등)
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

		CONSTRAINT fk_counselor_profile_user
        FOREIGN KEY (counselor_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
*/

    @Id
    @Column(name = "counselor_id", nullable = false)
    private Long counselor_id;

    @Column(name = "max_concurrent_chats", nullable = false)
    private Integer max_concurrent_chats;

    @Column(name = "is_active", nullable = false)
    private Boolean is_active;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "counselor_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_counselor_profile_user")
    )
    private User counselor;

    @PrePersist
    void prePersist() {
        if (max_concurrent_chats == null) max_concurrent_chats = 3;
        if (is_active == null) is_active = Boolean.TRUE;
    }

}
