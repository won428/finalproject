package com.example.demo.domain.course.mapping;

import com.example.demo.base.config.MapStructConfig;
import com.example.demo.domain.course.dto.CourseUpdateReq;
import com.example.demo.domain.course.entity.Course;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface CourseMapper {
    @BeanMapping(ignoreByDefault = true) // 명시한 것만 매핑 (나머지 필드는 무시)
    @Mapping(target = "courseName", source = "courseName")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "description", source = "description")
    void updateEntity(@MappingTarget Course entity, CourseUpdateReq req);
}
