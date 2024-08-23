//package team4.backend.security;
//
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.Authentication;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.Base64;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ActiveProfiles("test")
//public class JwtTokenProviderTest {
//
//	private static final String TEST_SECRET = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());
//	private static final int EXPIRATION_TIME_MS = 60000;
//
//	@InjectMocks // JwtTokenProvider 인스턴스를 생성하고 주입
//	private JwtTokenProvider jwtTokenProvider;
//
//	@Mock
//	private Authentication authentication;
//
//	@BeforeEach
//	public void setUp() {
//		jwtTokenProvider = new JwtTokenProvider(TEST_SECRET);
//		MockitoAnnotations.openMocks(this);
//	}
//
//	@Test // JWT 토큰 생성 테스트
//	public void testGenerateToken() {
//
//		team4.backend.entity.User user = team4.backend.entity.User.builder()
//			.email("test@example.com")
//			.build();
//
//		String token = jwtTokenProvider.generateToken(authentication);
//		assertNotNull(token);
//	}
//
//	@Test // JWT 토큰 유효성 검사 테스트
//	public void testValidateToken() {
//
//		team4.backend.entity.User user = team4.backend.entity.User.builder()
//			.userId(1L)
//			.email("test@example.com")
//			.build();
//
//		String token = jwtTokenProvider.generateToken(authentication);
//		assertTrue(jwtTokenProvider.validateToken(token));
//	}
//
//	@Test // JWT 토큰에서 사용자 이메일 추출 테스트
//	public void testGetUserEmailFromJWT() {
//
//		team4.backend.entity.User user = team4.backend.entity.User.builder()
//			.userId(1L)
//			.email("test@example.com")
//			.build();
//
//		String token = jwtTokenProvider.generateToken(authentication);
//		String email = jwtTokenProvider.getUserEmailFromJWT(token);
//		assertEquals("test@example.com", email);
//	}
//}
