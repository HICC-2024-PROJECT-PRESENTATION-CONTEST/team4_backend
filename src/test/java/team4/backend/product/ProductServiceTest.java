package team4.backend.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team4.backend.dto.ProductResponseDto;
import team4.backend.entity.Product;
import team4.backend.repository.ProductRepository;

import java.util.List;
import java.util.Arrays;
import team4.backend.service.ProductService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest implements ProductTest{

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @Test
  void testSearchProducts() {
    // Given
    String query = "test";
    List<Product> mockProductList = Arrays.asList(TEST_PRODUCT1, TEST_PRODUCT2);

    // Mocking: Repository가 검색 쿼리에 대해 예상되는 결과를 반환하도록 설정
    when(productRepository.findAllByProductName(query)).thenReturn(mockProductList);

    // When
    List<ProductResponseDto> result = productService.searchProducts(query);

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("test1", result.get(0).getProductName());
    assertEquals("test2", result.get(1).getProductName());

    // Verify that the repository was called exactly once
    verify(productRepository, times(1)).findAllByProductName(query);
  }
}
