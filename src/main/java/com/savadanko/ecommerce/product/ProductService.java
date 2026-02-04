package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.product.dto.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    ProductDTO addProduct(Product product, Long categoryId);
}
