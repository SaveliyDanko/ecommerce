package com.savadanko.ecommerce.product;

import com.savadanko.ecommerce.exceptions.ApiException;
import com.savadanko.ecommerce.exceptions.ResourceNotFoundException;
import com.savadanko.ecommerce.category.Category;
import com.savadanko.ecommerce.category.CategoryRepository;
import com.savadanko.ecommerce.file.FileService;
import com.savadanko.ecommerce.product.dto.ProductResponse;
import com.savadanko.ecommerce.product.dto.ProductList;
import com.savadanko.ecommerce.product.dto.ProductRequest;
import com.savadanko.ecommerce.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final ProductMapper mapper;

    @Override
    public ProductResponse addProduct(ProductRequest productRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Product product = mapper.toDomain(productRequest);

        boolean productExists = productRepository.existsByProductNameAndCategory(productRequest.getProductName(), category);

        if (productExists) {
            throw new ApiException("Product with name '" + productRequest.getProductName() + "' already exists in this category.");
        }

        product.setCategory(category);

        product.setImage("default.png");

        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();

        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);

        return mapper.toDto(savedProduct);
    }

    @Override
    public ProductList getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> productPage = productRepository.findAll(pageDetails);

        List<Product> products = productPage.getContent();

        if (products.isEmpty())
            throw new ApiException("No product created till now");

        List<ProductResponse> productResponses = products.stream().map(mapper::toDto).toList();

        ProductList productList = new ProductList();
        productList.setContent(productResponses);
        productList.setPageNumber(pageNumber);
        productList.setPageSize(pageSize);
        productList.setTotalElements(productPage.getTotalElements());
        productList.setTotalPages(productPage.getTotalPages());
        productList.setLastPage(productPage.isLast());

        return productList;
    }

    @Override
    public ProductList searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category, pageDetails);

        List<Product> products = productPage.getContent();

        List<ProductResponse> productResponses = products.stream()
                .map(mapper::toDto)
                .toList();

        ProductList productList = new ProductList();
        productList.setContent(productResponses);
        productList.setPageNumber(pageNumber);
        productList.setPageSize(pageSize);
        productList.setTotalElements(productPage.getTotalElements());
        productList.setTotalPages(productPage.getTotalPages());
        productList.setLastPage(productPage.isLast());

        return productList;
    }

    @Override
    public ProductList searchByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%', pageDetails);

        List<Product> products = productPage.getContent();

        List<ProductResponse> productResponses = products.stream()
                .map(mapper::toDto)
                .toList();

        ProductList productList = new ProductList();
        productList.setContent(productResponses);
        productList.setPageNumber(pageNumber);
        productList.setPageSize(pageSize);
        productList.setTotalElements(productPage.getTotalElements());
        productList.setTotalPages(productPage.getTotalPages());
        productList.setLastPage(productPage.isLast());

        return productList;
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product id", productId));

        mapper.updateEntityFromDto(productRequest, product);

        product.setImage("default.png");

        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
        product.setSpecialPrice(specialPrice);

        return mapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductResponse deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product id", productId));

        productRepository.delete(product);

        return mapper.toDto(product);
    }

    @Override
    public ProductResponse updateProductImage(Long productId, MultipartFile imageFile) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("Product", "product id", productId));

        String fileName = fileService.uploadFile(imageFile);

        product.setImage(fileName);

        return mapper.toDto(productRepository.save(product));
    }
}
