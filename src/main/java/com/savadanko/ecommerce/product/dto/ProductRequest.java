package com.savadanko.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String productName;

    private String description;

    private Double price;

    private Double discount;

    private Integer quantity;
}
