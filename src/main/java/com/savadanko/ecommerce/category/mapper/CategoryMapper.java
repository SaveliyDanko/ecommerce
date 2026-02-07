package com.savadanko.ecommerce.category.mapper;

import com.savadanko.ecommerce.category.Category;
import com.savadanko.ecommerce.category.dto.CategoryResponse;
import com.savadanko.ecommerce.category.dto.CategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toDto(Category product);
    Category toDomain(CategoryRequest request);
    void updateEntityFromDto(CategoryRequest request, @MappingTarget Category entity);
}