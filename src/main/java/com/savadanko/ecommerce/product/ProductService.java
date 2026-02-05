package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.product.dto.ProductDTO;
import com.savadanko.ecommerce.product.dto.ProductList;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    ProductDTO addProduct(Product product, Long categoryId);

    ProductList getAllProducts();

    ProductList searchByCategory(Long categoryId);

    ProductList searchByKeyword(String keyword);
}
