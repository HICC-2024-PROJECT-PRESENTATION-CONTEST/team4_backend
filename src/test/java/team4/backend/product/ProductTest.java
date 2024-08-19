package team4.backend.product;

import team4.backend.entity.Product;

public interface ProductTest {

  // 테스트에 사용할 상수들 정의
  Long TEST_PRODUCT_ID = 1L;
  String TEST_BRAND = "TestBrand";
  String TEST_PRODUCT_NAME = "TestProduct";
  Integer TEST_PRICE = 10000;
  Integer TEST_DISCOUNT_RATE = 10;
  Integer TEST_ORIGINAL_RATE = 9000;
  Integer TEST_LIKES = 100;
  String TEST_PRODUCT_URL = "http://test.com/product";
  String TEST_IMAGE_URL = "http://test.com/image.jpg";
  String TEST_PRODUCT_CODE = "TEST1234";

  // 테스트에 사용할 Product 객체 정의
  Product TEST_PRODUCT1 = Product.builder()
      .id(TEST_PRODUCT_ID)
      .brand(TEST_BRAND)
      .productName("test1")
      .price(TEST_PRICE)
      .discountRate(TEST_DISCOUNT_RATE)
      .originalPrice(TEST_ORIGINAL_RATE)
      .likes(TEST_LIKES)
      .productURL(TEST_PRODUCT_URL)
      .imageURL(TEST_IMAGE_URL)
      .productCode(TEST_PRODUCT_CODE)
      .build();

  // 다른 Product 객체 정의
  Product TEST_PRODUCT2 = Product.builder()
      .id(2L)
      .brand("AnotherBrand")
      .productName("test2")
      .price(20000)
      .discountRate(20)
      .originalPrice(16000)
      .likes(200)
      .productURL("http://test.com/anotherproduct")
      .imageURL("http://test.com/anotherimage.jpg")
      .productCode("ANOTHER1234")
      .build();
}
