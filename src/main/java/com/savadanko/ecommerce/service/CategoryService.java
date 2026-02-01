package com.savadanko.ecommerce.service;

import com.savadanko.ecommerce.dto.CategoryResponse;
import com.savadanko.ecommerce.model.Category;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);
}
