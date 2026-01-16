package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "")
/* 소셜 로그인 테이블 */
public class UserOauthAccounts {
//    변수명	                    내용	                                                            규격	                    제약조건
//    id			                                                                                                    PK
//    user_id			                                                                                                FK
//    provider	                어느 소셜 제공자인지                                               VARCHAR(20)	            UNIQUE, NOT NULL
//                              GOOGLE / KAKAO / NAVER

//    provider_user_id	        제공자가 부여한 해당 유저의 식별자	                                VARCHAR(255)	        UNIQUE, NOT NULL
//    provider_email	        소셜 제공자가 내려준 사용자의 이메일	                                VARCHAR(255)	        NULL
//    provider_email_verified	검증된 이메일인지 여부
//                              (미검증 = 0/ 검증 = 1)	                                        TINYINT(1)	            NULL

//    created_at	            해당 행이 생성된 시각	                                            DATETIME	            NOT NULL DEFAULT CURRENT_TIMESTAMP
//    updated_at	            해당 행이 수정된 시각	                                            DATETIME	            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
//    linked_at	                소셜계정이 유저 계정에 처음 붙은 시각
//                              = 소셜로그인으로 최초 가입한 날짜	                                DATETIME	            NOT NULL DEFAULT CURRENT_TIMESTAMP

//    last_login_at	            마지막으로 로그인한 시각(로그아웃 시간 아님)	                        DATETIME	            NULL
//    revoked_at	            “구글 연결 해제” 같은 기능으로 소셜 로그인을 더 이상 쓰지 않게 한 시각	DATETIME	            NULL
}
