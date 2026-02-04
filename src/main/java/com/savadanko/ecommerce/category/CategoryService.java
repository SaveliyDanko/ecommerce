package com.savadanko.ecommerce.category;

import com.savadanko.ecommerce.category.dto.CategoryDTO;
import com.savadanko.ecommerce.category.dto.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
