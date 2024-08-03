package team4.backend.service;

import team4.backend.entity.User;
import team4.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test // 사용자 저장 테스트
	public void testSaveUser() {
		User user = User.builder().email("test@example.com").build();

		when(userRepository.save(any(User.class))).thenReturn(user);

		User savedUser = userService.save(user);
		assertEquals("test@example.com", savedUser.getEmail());
	}

	@Test // 이메일로 사용자 찾기 테스트
	public void testFindByEmail() {
		User user = User.builder().email("test@example.com").build();

		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

		Optional<User> foundUser = userService.findByEmail("test@example.com");
		assertTrue(foundUser.isPresent());
		assertEquals("test@example.com", foundUser.get().getEmail());
	}
}
