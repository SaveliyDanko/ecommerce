package com.savadanko.ecommerce.product.mapper;

import com.savadanko.ecommerce.product.Product;
import com.savadanko.ecommerce.product.dto.ProductResponse;
import com.savadanko.ecommerce.product.dto.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toDto(Product product);
    Product toDomain(ProductRequest request);
    void updateEntityFromDto(ProductRequest request, @MappingTarget Product entity);
}
