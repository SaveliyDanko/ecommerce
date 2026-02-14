package com.savadanko.ecommerce.product.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 255)
    private String productName;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @Min(0)
    @Max(100)
    private Double discount;

    @NotNull
    @Min(0)
    @Max(1000)
    private Integer quantity;
}
