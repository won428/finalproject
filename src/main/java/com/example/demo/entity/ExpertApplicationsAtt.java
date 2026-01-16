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
/* 전문가 신청내역 파일첨부 */
public class ExpertApplicationsAtt {
    //    변수명	                내용	                    규격	        제약조건
//    id	                    테이블 PK	            BIGINT	    PK
//    expert_application_id	    신청 내역 FK	            BIGINT	    FK
//    storage_provider	        스토리지명 (현재 S3)	    VARCHAR	    NOT NULL
//    s3_region	                리전명	                VARCHAR 	NOT NULL
//    s3_key	                S3 키(객체 식별을 위한 키)	VARCHAR 	NOT NULL
//    s3_bucket	                버킷명	                VARCHAR 	NOT NULL
//    content_type	            컨텐츠타입	            VARCHAR 	NOT NULL
//    file_size_bytes	        파일 사이즈	            BIGINT  	NOT NULL
//    checksum	                해쉬나 태그	            VARCHAR	    NOT NULL
//    created_at	            등록 날짜	            DATETIME	NOT NULL DEFAULT CURRENT_TIMESTAMP
}
