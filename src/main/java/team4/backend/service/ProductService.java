package team4.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import team4.backend.dto.ProductResponseDto;
import team4.backend.entity.Product;
import team4.backend.exception.BusinessException;
import team4.backend.exception.ErrorCode;
import team4.backend.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public void createProduct(Product product) {
    productRepository.save(product);
  }

  public List<ProductResponseDto> searchProducts(String query) {
    List<Product> productList = productRepository.findByProductNameContaining(query);

    return productList.stream()
        .map(ProductResponseDto::new)
        .toList();
  }

  public ProductResponseDto searchProduct(Long productId) {
    return new ProductResponseDto(productRepository.findById(productId)
        .orElseThrow(
            () -> new BusinessException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_PRODUCT)));
  }
}
