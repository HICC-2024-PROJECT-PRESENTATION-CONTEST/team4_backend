package team4.backend.service;

import team4.backend.entity.User;
import team4.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class UserDetailsServiceImplTest {

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test // 사용자 이름으로 사용자 로드 테스트
	public void testLoadUserByUsername() {

		User user = User.builder().email("test@example.com").build();

		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

		UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");
		assertEquals("test@example.com", userDetails.getUsername());
	}

	@Test // 사용자 이름으로 사용자를 찾지 못했을 때의 테스트
	public void testLoadUserByUsernameNotFound() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

		assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsService.loadUserByUsername("notfound@example.com");
		});
	}
}
