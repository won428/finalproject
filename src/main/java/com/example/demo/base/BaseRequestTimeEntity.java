package com.example.demo.base;

import com.example.demo.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseRequestTimeEntity extends BasePkEntity {

    @CreationTimestamp
    @Column(name = "submitted_at", updatable = false, nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
}
