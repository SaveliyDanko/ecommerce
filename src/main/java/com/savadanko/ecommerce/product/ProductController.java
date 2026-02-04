package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(
            @RequestBody Product product,
            @PathVariable Long categoryId)
    {
        ProductDTO response = productService.addProduct(product, categoryId);
        return ResponseEntity.ok(response);
    }
}
