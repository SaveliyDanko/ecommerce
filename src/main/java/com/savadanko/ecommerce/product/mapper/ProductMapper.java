package com.savadanko.ecommerce.product.mapper;

import com.savadanko.ecommerce.product.Product;
import com.savadanko.ecommerce.product.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toDomain(ProductDTO productDTO);
}
