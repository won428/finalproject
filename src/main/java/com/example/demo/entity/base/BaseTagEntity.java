package com.example.demo.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@MappedSuperclass // 테이블로 생성되지 않고 자식 클래스에게 매핑 정보만 제공
public abstract class BaseTagEntity {

    @Id
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Id
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

}
