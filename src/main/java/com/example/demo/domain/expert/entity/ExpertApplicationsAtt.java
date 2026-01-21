package com.example.demo.domain.expert.entity;

import com.example.demo.base.entity.BaseAttEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"expertApplication"})
@Entity
@Table(name = "expert_applications_att")
/* 전문가 신청내역 파일첨부 */
public class ExpertApplicationsAtt extends BaseAttEntity {
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
//    original_name

    // FK 컬럼(쓰기용)
    @Column(name = "expert_application_id", nullable = false)
    private Long expertApplicationId;

    // 연관관계(읽기전용)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "expert_application_id",
            nullable = false,
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_expert_app_att_application")
    )
    private ExpertApplication expertApplication;
}
