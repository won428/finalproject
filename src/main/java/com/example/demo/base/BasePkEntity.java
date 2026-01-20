package com.example.demo.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass // 테이블로 생성되지 않고 자식 클래스에게 매핑 정보만 제공
public abstract class BasePkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
