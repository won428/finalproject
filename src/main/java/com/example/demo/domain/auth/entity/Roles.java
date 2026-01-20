package com.example.demo.domain.auth.entity;

import com.example.demo.base.BasePkEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_role_code", columnNames = {"code"})
        }
)
/* 유저 역할 */
public class Roles extends BasePkEntity {
//    변수명   	내용                  	규격	            제약조건
//    id		                        BIGINT	        AUTO_INCREMENT PK
//    code	    역할 코드
//              STUDENT/                VARCHAR(30)	    UNIQUE, NOT NULL
//              INSTRUCTOR/
//              HR_ADMIN/
//              CONTENT_ADMIN/
//              ACCOUNT_ADMINCOUNSELOR/

//    name	    표시명	                VARCHAR(50)	    NOT NULL

    @Column(name = "code", nullable = false, length = 50, unique = true)
    private String code;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

}
