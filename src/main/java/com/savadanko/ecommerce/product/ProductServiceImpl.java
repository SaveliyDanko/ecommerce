package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.exceptions.ResourceNotFoundException;
import com.savadanko.ecommerce.category.Category;
import com.savadanko.ecommerce.category.CategoryRepository;
import com.savadanko.ecommerce.file.FileService;
import com.savadanko.ecommerce.product.dto.ProductResponse;
import com.savadanko.ecommerce.product.dto.ProductList;
import com.savadanko.ecommerce.product.dto.ProductRequest;
import com.savadanko.ecommerce.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final ProductMapper mapper;

    @Override
    public ProductResponse addProduct(ProductRequest productRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Product product = mapper.toDomain(productRequest);

        product.setCategory(category);

        product.setImage("default.png");

        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();

        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);

        return mapper.toDto(savedProduct);
    }

    @Override
    public ProductList getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponses = products.stream().map(mapper::toDto).toList();

        return new ProductList(productResponses);
    }

    @Override
    public ProductList searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);

        List<ProductResponse> productResponses = products.stream()
                .map(mapper::toDto)
                .toList();

        return new ProductList(productResponses);
    }

    @Override
    public ProductList searchByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');

        List<ProductResponse> productResponses = products.stream()
                .map(mapper::toDto)
                .toList();

        return new ProductList(productResponses);
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product id", productId));

        mapper.updateEntityFromDto(productRequest, product);

        product.setImage("default.png");

        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
        product.setSpecialPrice(specialPrice);

        return mapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductResponse deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product id", productId));

        productRepository.delete(product);

        return mapper.toDto(product);
    }

    @Override
    public ProductResponse updateProductImage(Long productId, MultipartFile imageFile) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("Product", "product id", productId));

        String fileName = fileService.uploadFile(imageFile);

        product.setImage(fileName);

        return mapper.toDto(productRepository.save(product));
    }
}
