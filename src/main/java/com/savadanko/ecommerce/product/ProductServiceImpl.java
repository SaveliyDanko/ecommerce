package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.exceptions.ResourceNotFoundException;
import com.savadanko.ecommerce.category.Category;
import com.savadanko.ecommerce.category.CategoryRepository;
import com.savadanko.ecommerce.product.dto.ProductDTO;
import com.savadanko.ecommerce.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    @Override
    public ProductDTO addProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        product.setCategory(category);

        return mapper.toDto(productRepository.save(product));
    }
}
