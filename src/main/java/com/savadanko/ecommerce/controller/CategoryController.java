package com.savadanko.ecommerce.controller;

import com.savadanko.ecommerce.dto.CategoryResponse;
import com.savadanko.ecommerce.model.Category;
import com.savadanko.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(){
        CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        String status = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(status);
    }


    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid
                                                 @RequestBody Category category,
                                                 @PathVariable Long categoryId){
        Category savedCategory = categoryService.updateCategory(category, categoryId);
        return ResponseEntity.ok("Category with category id: " + savedCategory.getCategoryId() + " updated successfully");
    }
}
