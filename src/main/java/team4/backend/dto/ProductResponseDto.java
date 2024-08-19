package team4.backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team4.backend.entity.PriceHistory;
import team4.backend.entity.Product;

@Getter
@AllArgsConstructor
public class ProductResponseDto {

  private Long id;
  private String brand;
  private String productName;
  private Integer price;
  private Integer discountRate;
  private Integer originalPrice;
  private Integer likes;
  private String productURL;
  private String imageURL;
  private String productCode;

  private List<PriceHistory> priceHistoryList;


  public ProductResponseDto(Product product){
    this.id = product.getId();
    this.brand = product.getBrand();
    this.productName = product.getProductName();
    this.price = product.getPrice();
    this.discountRate = product.getDiscountRate();
    this.originalPrice = product.getOriginalPrice();
    this.likes = product.getLikes();
    this.productURL = product.getProductURL();
    this.productCode = product.getProductCode();
    this.priceHistoryList = product.getPriceHistoryList();
  }
}
