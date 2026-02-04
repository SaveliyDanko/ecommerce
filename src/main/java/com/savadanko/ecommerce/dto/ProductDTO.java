package com.savadanko.ecommerce.dto;

import com.savadanko.ecommerce.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;

    private String name;

    // private String description;

    private Double discount;

    private String image;

    private Double price;

    private Integer quantity;

    private Double specialPrice;

    // private Category category;
}
