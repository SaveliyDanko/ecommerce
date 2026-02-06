package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.product.dto.ProductResponse;
import com.savadanko.ecommerce.product.dto.ProductList;
import com.savadanko.ecommerce.product.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductResponse> addProduct(
            @RequestBody ProductRequest product,
            @PathVariable Long categoryId)
    {
        ProductResponse response = productService.addProduct(product, categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductList> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<ProductList> getProductsByCategory(@PathVariable Long categoryId) {
        ProductList productList = productService.searchByCategory(categoryId);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("public/products/keyword/{keyword}")
    public ResponseEntity<ProductList> getProductsByKeyword(@PathVariable String keyword) {
        ProductList productList = productService.searchByKeyword(keyword);
        return ResponseEntity.ok(productList);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest product,
                                                         @PathVariable Long productId) {

        ProductResponse productResponse = productService.updateProduct(product, productId);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long productId) {
        ProductResponse productResponse = productService.deleteProduct(productId);
        return ResponseEntity.ok(productResponse);
    }
}
