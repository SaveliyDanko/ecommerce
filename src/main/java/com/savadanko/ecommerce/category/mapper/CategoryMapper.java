package com.savadanko.ecommerce.category.mapper;

import com.savadanko.ecommerce.category.Category;
import com.savadanko.ecommerce.category.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category product);
    Category toDomain(CategoryDTO productDTO);
}