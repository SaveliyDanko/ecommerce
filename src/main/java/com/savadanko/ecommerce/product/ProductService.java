package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.product.dto.ProductResponse;
import com.savadanko.ecommerce.product.dto.ProductList;
import com.savadanko.ecommerce.product.dto.ProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ProductService {
    ProductResponse addProduct(ProductRequest product, Long categoryId);

    ProductList getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductList searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductList searchByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse updateProduct(ProductRequest product, Long productId);

    ProductResponse deleteProduct(Long productId);

    ProductResponse updateProductImage(Long Id, MultipartFile image);
}
