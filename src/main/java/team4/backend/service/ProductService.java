package team4.backend.service;

import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team4.backend.dto.ProductResponseDto;
import team4.backend.entity.PriceHistory;
import team4.backend.entity.Product;
import team4.backend.exception.BusinessException;
import team4.backend.exception.ErrorCode;
import team4.backend.repository.PriceHistoryRepository;
import team4.backend.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final PriceHistoryRepository priceHistoryRepository;
  private final LikeService likeService;

  public void createProduct(List<Product> productList) {
    for (Product product : productList) {
      PriceHistory priceHistory = new PriceHistory(new Date(), product.getPrice(), product);
      productRepository.save(product);
      priceHistoryRepository.save(priceHistory);
      boolean isPriceDropped = product.getPrice() > priceHistory.getPrice();
      if (isPriceDropped) {
        likeService.notifyPriceDrop(product.getId());
      }
    }
  }

  public void createPriceHistory(PriceHistory priceHistory, Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(
            () -> new BusinessException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_PRODUCT));

    boolean isPriceDropped = product.getPrice() > priceHistory.getPrice();

    priceHistory.setProduct(product);
    priceHistoryRepository.save(priceHistory);

    if (isPriceDropped) {
      likeService.notifyPriceDrop(productId);
    }
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

  public String getProductNameById(Long productId) {
    return productRepository.findById(productId)
        .map(Product::getProductName)
        .orElseThrow(
            () -> new BusinessException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_PRODUCT));
  }

  public List<String> autoComplete(String query) {
    return productRepository.findTop10ByProductNameContaining(query).stream()
        .map(Product::getProductName).toList();
  }
}
