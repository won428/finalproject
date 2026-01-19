package com.example.demo.entity;


import com.example.demo.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/* 사용자 */
public  class User extends BaseTimeEntity {
//    변수명	내용	규격	제약조건
//    id		                                                BIGINT	            PK
//    nickname	        유저 닉네임	                            VARCHAR(50)	        UNIQUE
//    birth	            생년월일	                                DATE	            NULL
//    status	        상태                                     VARCHAR(20)	        NOT NULL DEFAULT ‘ACTIVE’
//                      활성화 / 정지 / 탈퇴/ 휴면
//                      ACTIVE/ SUSPEND /WITHDRAWN / DORMANT
//    address	        큰 주소(도로명 주소까지)	                VARCHAR(50)	        NULL
//    address_detail	상세주소(도로명 주소 이후)	                VARCHAR(50)	        NULL
//    created_at	    행이 생성된 날짜	                        DATETIME	        NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	    행 수정된 날짜(닉네임 변경, 생년월일 변경 등)	DATETIME	        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
//    deleted_at	    탈퇴일                                   DATETIME	        NULL
//                      탈퇴시 계정삭제 말고 상태를 탈퇴로 바꾼후
//                      개인정보 보관 기간 지나면 실제 삭제
//    last_login_at	    마지막 로그인	                            DATETIME	        NULL

    @Column(name = "nickname", nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "ACTIVE";

    @Column(name = "address") // length 속성 default가 255임.
    private String address;

    @Column(name = "address_detail") // length 속성 default가 255임.
    private String addressDetail;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;



/*  [comments]
1. 소셜로그인으로 회원가입 시 기본정보만 이곳에 저장되고 소셜로그인 관련 정보는 user_oauth_accounts 테이블에 저장됨.
2. 로컬로그인(ID, PW)과 소셜로그인(API) 둘다 사용가능하려면 password는 null 허용이어야 함. ← 소셜로그인은 PW 안쓰기 때문.
3. 계정 탈퇴시 계정을 바로 삭제하는 것이 아니라 개인정보 보관기간 지나면(ex: 1년) deleted_at 컬럼 기준으로 삭제.
 */
}
