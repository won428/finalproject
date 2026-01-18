package com.example.demo.entity.base;

import com.example.demo.enums.StorageProvider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass // 테이블로 생성되지 않고 자식 클래스에게 매핑 정보만 제공
public abstract class BaseAttEntity extends BasePkEntity{

    @Column(name = "storage_provider",nullable = false, updatable=false, length = 20)
    @Enumerated(EnumType.STRING)
    private StorageProvider storageProvider;

    @Column(name = "s3_key", nullable = false, updatable=false, length = 1024)
    private String s3Key;

    @Column(name = "content_type", nullable = false, updatable=false,  length = 255)
    private String contentType;

    @Column(name = "file_size_bytes", updatable=false, nullable = false)
    private Long fileSizeBytes;

    @Column(name = "checksum", nullable = false, updatable=false, length = 64)
    private String checksum;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false) // 수정 시에는 건드리지 않음
    private LocalDateTime createdAt;
}
