package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.product.dto.ProductResponse;
import com.savadanko.ecommerce.product.dto.ProductList;
import com.savadanko.ecommerce.product.dto.ProductRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    ProductResponse addProduct(ProductRequest product, Long categoryId);

    ProductList getAllProducts();

    ProductList searchByCategory(Long categoryId);

    ProductList searchByKeyword(String keyword);

    ProductResponse updateProduct(ProductRequest product, Long productId);

    ProductResponse deleteProduct(Long productId);
}
