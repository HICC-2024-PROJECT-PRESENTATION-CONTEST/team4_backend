package team4.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team4.backend.entity.User;
import team4.backend.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// 사용자 정보를 저장하는 메소드
	public User save(User user) {
		return userRepository.save(user);
	}

	// 이메일을 통해 사용자 정보를 찾는 메소드
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// 이메일을 통해 사용자 정보를 삭제하는 메소드
	public void deleteByEmail(String email) {
		userRepository.deleteByEmail(email);
	}
}
