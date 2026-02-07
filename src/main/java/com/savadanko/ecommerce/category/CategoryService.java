package com.savadanko.ecommerce.category;

import com.savadanko.ecommerce.category.dto.CategoryResponse;
import com.savadanko.ecommerce.category.dto.CategoryRequest;
import com.savadanko.ecommerce.category.dto.CategoryList;

public interface CategoryService {
    CategoryList getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    CategoryResponse createCategory(CategoryRequest category);

    CategoryResponse deleteCategory(Long categoryId);

    CategoryResponse updateCategory(CategoryRequest categoryDTO, Long categoryId);
}
