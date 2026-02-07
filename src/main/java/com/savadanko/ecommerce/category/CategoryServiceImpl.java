package com.savadanko.ecommerce.category;

import com.savadanko.ecommerce.category.dto.CategoryResponse;
import com.savadanko.ecommerce.category.dto.CategoryRequest;
import com.savadanko.ecommerce.category.dto.CategoryList;
import com.savadanko.ecommerce.category.mapper.CategoryMapper;
import com.savadanko.ecommerce.exceptions.ApiException;
import com.savadanko.ecommerce.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;


    @Override
    public CategoryList getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();

        if (categories.isEmpty())
            throw new ApiException("No category created till now");

        List<CategoryResponse> categoryResponses = categories.stream()
                .map(mapper::toDto)
                .toList();


        // Build Response
        CategoryList categoryList = new CategoryList();
        categoryList.setContent(categoryResponses);
        categoryList.setPageNumber(categoryPage.getNumber());
        categoryList.setPageSize(categoryPage.getSize());
        categoryList.setTotalElements(categoryPage.getTotalElements());
        categoryList.setTotalPages(categoryPage.getTotalPages());
        categoryList.setLastPage(categoryPage.isLast());

        return categoryList;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = mapper.toDomain(categoryRequest);
        Category findedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (findedCategory != null)
            throw new ApiException("Category with name: " + category.getCategoryName() + " already exists");
        Category savedCategory = categoryRepository.save(category);
        return mapper.toDto(savedCategory);
    }

    @Override
    public CategoryResponse deleteCategory(Long categoryId) {
        Category savedCategory = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(savedCategory);
        return mapper.toDto(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        mapper.updateEntityFromDto(categoryRequest, category);

        return mapper.toDto(categoryRepository.save(category));
    }
}

