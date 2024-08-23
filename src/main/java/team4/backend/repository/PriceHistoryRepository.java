package team4.backend.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import team4.backend.entity.PriceHistory;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory,Long> {

  Optional<PriceHistory> findByDate(LocalDate date);
}
