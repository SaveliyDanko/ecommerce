package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private String description;

    private Double discount;

    private String image;

    private Double price;

    private Integer quantity;

    private Double specialPrice;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
