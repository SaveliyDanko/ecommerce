package com.savadanko.ecommerce.category;

import com.savadanko.ecommerce.category.dto.CategoryRequest;
import com.savadanko.ecommerce.category.dto.CategoryResponse;
import com.savadanko.ecommerce.config.AppConstants;
import com.savadanko.ecommerce.category.dto.CategoryList;
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
    public ResponseEntity<CategoryList> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_CATEGORIES_ORDER, required = false) String sortOrder
    ){
        CategoryList categoryList = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse createdCategoryResponse = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(createdCategoryResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long categoryId){
        CategoryResponse deletedCategoryResponse = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(deletedCategoryResponse);
    }


    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
                                                 @Valid
                                                 @RequestBody CategoryRequest categoryRequest,
                                                 @PathVariable Long categoryId){
        CategoryResponse updatedCategoryResponse = categoryService.updateCategory(categoryRequest, categoryId);
        return ResponseEntity.ok(updatedCategoryResponse);
    }
}
