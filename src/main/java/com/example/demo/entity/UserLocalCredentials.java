package com.example.demo.entity;

import com.example.demo.entity.base.BasePkEntity;
import com.example.demo.entity.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"password"})
@Entity
@Table(name = "user_local_credentials")
/* 로컬 로그인 테이블 */
public class UserLocalCredentials extends BaseTimeEntity {
//    변수명	내용	규격	제약조건
//    id		                                            BIGINT	        PK
//    user_id	            유저 닉네임	                    VARCHAR(50)	    FK
//    email	                이메일	                        VARCHAR(255)	UNIQUE
//    email_verified_at	    이메일 인증 시각	                DATETIME	    NULL
//    password	        암호화된 비밀번호	                VARCHAR(255)	NULL
//    password_updated_at	비밀번호 변경일자	                DATETIME	    NULL
//    created_at	        행이 생성된 날짜	                DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	        행 수정된 날짜(비밀번호 변경 등)	    DATETIME	    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

    // user_id BIGINT NOT NULL, FK -> users(id) ON DELETE CASCADE
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    // email VARCHAR(100) NOT NULL UNIQUE
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    // password VARCHAR(255) NOT NULL  (변수명은 password_hash 유지)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    // email_verified_at DATETIME NULL
    @Column(name = "email_verified_at")
    private LocalDateTime email_verified_at;

    // password_updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    @Column(name = "password_updated_at", nullable = false)
    private LocalDateTime password_updated_at;

    @PrePersist
    void prePersist() {
        // DB default에 의존하지 않도록 JPA에서도 보장
        if (password_updated_at == null) {
            password_updated_at = LocalDateTime.now();
        }
    }

/*  [comments] 
1. ID(이메일)와 PW로 로그인하는 일반 방식.
2. 이메일 인증 NULL 허용
3. 로컬 로그인 시 PW 암호화 필요.
 */
}
