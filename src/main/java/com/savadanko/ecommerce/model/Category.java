package com.savadanko.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Entity(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank
    @Size(min = 2, message = "Category name must contain at least 2 characters")
    private String categoryName;
}
