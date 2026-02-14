package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.config.AppConstants;
import com.savadanko.ecommerce.product.dto.ProductResponse;
import com.savadanko.ecommerce.product.dto.ProductList;
import com.savadanko.ecommerce.product.dto.ProductRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductResponse> addProduct(
            @Valid @RequestBody ProductRequest product,
            @PathVariable @Positive Long categoryId)
    {
        ProductResponse savedProduct = productService.addProduct(product, categoryId);

        return ResponseEntity
                .created(URI.create("/api/public/products/" + savedProduct.getProductId()))
                .body(savedProduct);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductList> getAllProduct(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_PRODUCTS_ORDER, required = false) String sortOrder
    ) {
        return ResponseEntity.ok(productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<ProductList> getProductsByCategory(
            @PathVariable @Positive Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_PRODUCTS_ORDER, required = false) String sortOrder
    ) {
        ProductList productList = productService.searchByCategory(
                categoryId,
                pageNumber,
                pageSize,
                sortBy,
                sortOrder
        );
        return ResponseEntity.ok(productList);
    }

    @GetMapping("public/products/keyword/{keyword}")
    public ResponseEntity<ProductList> getProductsByKeyword(
            @PathVariable @NotBlank String keyword,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_PRODUCTS_ORDER, required = false) String sortOrder
    ) {
        ProductList productList = productService.searchByKeyword(
                keyword,
                pageNumber,
                pageSize,
                sortBy,
                sortOrder
        );

        return ResponseEntity.ok(productList);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@Valid @RequestBody ProductRequest product,
                                                         @PathVariable Long productId) {

        ProductResponse productResponse = productService.updateProduct(product, productId);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable @Positive Long productId) {
        ProductResponse productResponse = productService.deleteProduct(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductResponse> updateProductImage(@PathVariable @Positive Long productId,
                                                              @RequestParam("image") MultipartFile image) {
        ProductResponse productResponse = productService.updateProductImage(productId, image);
        return ResponseEntity.ok(productResponse);
    }
}
