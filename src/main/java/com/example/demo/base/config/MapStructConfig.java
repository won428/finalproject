package com.example.demo.base.config;

import org.mapstruct.*;

@MapperConfig(
        componentModel = "spring",

        // DI는 생성자 주입으로 고정 (테스트/순환참조 파악이 쉬움)
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        // DTO/엔터티 매핑에서 빠진 필드가 있으면 컴파일 에러로 막음
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        // null 체크 전략(불필요한 NPE 방지, 코드 생성 안정화)
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        // update(부분수정) 매핑에서 source가 null이면 target을 건드리지 않음
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public class MapStructConfig {
}
