package team4.backend.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import team4.backend.entity.Role;

import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class JwtTokenProviderTest {

	private static final String TEST_SECRET = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());
	private static final int EXPIRATION_TIME_MS = 60000;

	@InjectMocks
	private JwtTokenProvider jwtTokenProvider;

	@Mock
	private Authentication authentication;

	@BeforeEach
	public void setUp() {
		jwtTokenProvider = new JwtTokenProvider(TEST_SECRET, EXPIRATION_TIME_MS);
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGenerateToken() {
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);

		team4.backend.entity.User user = team4.backend.entity.User.builder()
			.id(1L) // 수동으로 ID 설정
			.email("test@example.com")
			.role(roles)
			.build();

		CustomOAuth2User customOAuth2User = new CustomOAuth2User(user, Collections.emptyMap());

		when(authentication.getPrincipal()).thenReturn(customOAuth2User);

		String token = jwtTokenProvider.generateToken(authentication);
		assertNotNull(token);
	}

	@Test
	public void testValidateToken() {
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);

		team4.backend.entity.User user = team4.backend.entity.User.builder()
			.id(1L) // 수동으로 ID 설정
			.email("test@example.com")
			.role(roles)
			.build();

		CustomOAuth2User customOAuth2User = new CustomOAuth2User(user, Collections.emptyMap());

		when(authentication.getPrincipal()).thenReturn(customOAuth2User);

		String token = jwtTokenProvider.generateToken(authentication);
		assertTrue(jwtTokenProvider.validateToken(token));
	}

	@Test
	public void testGetUserEmailFromJWT() {
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);

		team4.backend.entity.User user = team4.backend.entity.User.builder()
			.id(1L) // 수동으로 ID 설정
			.email("test@example.com")
			.role(roles)
			.build();

		CustomOAuth2User customOAuth2User = new CustomOAuth2User(user, Collections.emptyMap());

		when(authentication.getPrincipal()).thenReturn(customOAuth2User);

		String token = jwtTokenProvider.generateToken(authentication);
		String email = jwtTokenProvider.getUserEmailFromJWT(token);
		assertEquals("test@example.com", email);
	}
}
