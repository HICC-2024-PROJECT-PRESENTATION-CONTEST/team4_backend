package team4.backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import team4.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

  List<Product> findByProductNameContaining(String query);
  List<Product> findTop10ByProductNameContaining(String query);
  Optional<Product> findById(Long productId);
}
