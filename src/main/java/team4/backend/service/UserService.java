package team4.backend.service;

import team4.backend.entity.User;
import team4.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findByProviderAndProviderId(String provider, String providerId) {
		return userRepository.findByProviderAndProviderId(provider, providerId);
	}

	public Optional<User> findByVerificationToken(String token) {
		return userRepository.findByVerificationToken(token);
	}

	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}
}
