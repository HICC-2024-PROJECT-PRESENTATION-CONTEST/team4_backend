package team4.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import team4.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findByProviderAndProviderId(String provider, String providerId);
	Optional<User> findByVerificationToken(String token);

	void deleteByEmail(String email);
}
