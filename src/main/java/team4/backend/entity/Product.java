package team4.backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = false)
  private String brand;

  @Column(nullable = false, unique = false)
  private String productName;

  @Column(nullable = false, unique = false)
  private Integer price;

  @Column(nullable = false, unique = false)
  private Integer discountRate;

  @Column(nullable = false, unique = false)
  private Integer originalPrice;

  @Column(nullable = false, unique = false)
  private Integer likes;

  @Column(nullable = false, unique = false)
  private String productURL;

  @Column(nullable = false, unique = false)
  private String imageURL;

  @OneToMany(mappedBy = "product")
  List<PriceHistory> priceHistoryList = new ArrayList<>();
}
