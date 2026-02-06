package com.savadanko.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long productId;

    private String productName;

    private String description;

    private Double discount;

    private String image;

    private Integer quantity;

    private Double price;

    private Double specialPrice;
}
