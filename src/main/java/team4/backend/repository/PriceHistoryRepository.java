package team4.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team4.backend.entity.PriceHistory;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory,Long> {

}
