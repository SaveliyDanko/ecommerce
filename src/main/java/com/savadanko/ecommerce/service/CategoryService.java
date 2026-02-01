package com.savadanko.ecommerce.service;

import com.savadanko.ecommerce.dto.CategoryDTO;
import com.savadanko.ecommerce.dto.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
