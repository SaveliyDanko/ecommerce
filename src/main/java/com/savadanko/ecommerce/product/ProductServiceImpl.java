package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.exceptions.ResourceNotFoundException;
import com.savadanko.ecommerce.category.Category;
import com.savadanko.ecommerce.category.CategoryRepository;
import com.savadanko.ecommerce.product.dto.ProductDTO;
import com.savadanko.ecommerce.product.dto.ProductList;
import com.savadanko.ecommerce.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        product.setImage("default.png");

        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
        product.setSpecialPrice(specialPrice);


        Product savedProduct = productRepository.save(product);

        return mapper.toDto(savedProduct);
    }

    @Override
    public ProductList getAllProducts() {
        ProductList productList = new ProductList();
        productList.setContent(productRepository.findAll().stream().map(mapper::toDto).toList());
        return productList;
    }

    @Override
    public ProductList searchByCategory(Long categoryId) {
        ProductList productList = new ProductList();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<ProductDTO> productDTOS = productRepository.findByCategoryOrderByPriceAsc(category).stream()
                .map(mapper::toDto)
                .toList();

        productList.setContent(productDTOS);

        return productList;
    }

    @Override
    public ProductList searchByKeyword(String keyword) {
        ProductList productList = new ProductList();

        List<ProductDTO> productDTOS = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%').stream()
                .map(mapper::toDto)
                .toList();

        productList.setContent(productDTOS);

        return productList;
    }
}
