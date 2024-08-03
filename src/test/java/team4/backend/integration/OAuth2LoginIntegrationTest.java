package team4.backend.integration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import team4.backend.entity.Role;
import team4.backend.entity.User;
import team4.backend.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OAuth2LoginIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	public void setup() {
		// 테스트 간 간섭을 피하기 위해 리포지토리를 초기화
		userRepository.deleteAll();

		// Create a mock user
		User user = new User("user@example.com", Collections.singleton(Role.USER));
		userRepository.save(user);
	}

	@TestConfiguration
	static class TestConfig {

		@Bean
		@Primary
		public ClientRegistrationRepository clientRegistrationRepository() {
			ClientRegistration googleRegistration = ClientRegistration.withRegistrationId("google")
				.clientId("google-client-id")
				.clientSecret("google-client-secret")
				.scope("email", "profile")
				.authorizationUri("https://accounts.google.com/o/oauth2/auth")
				.tokenUri("https://oauth2.googleapis.com/token")
				.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
				.userNameAttributeName("id")
				.clientName("Google")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
				.build();

			return new InMemoryClientRegistrationRepository(googleRegistration);
		}

		@Bean
		@Primary
		public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
			return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
		}

		@Bean
		@Primary
		public OAuth2AuthorizedClientRepository authorizedClientRepository(OAuth2AuthorizedClientService authorizedClientService) {
			return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
		}
	}

	@Test // 공개 엔드포인트 테스트
	public void testPublicEndpoint() throws Exception {
		mockMvc.perform(get("/api/auth/public"))
			.andExpect(status().isOk())
			.andExpect(content().string("Public endpoint"));
	}

	@Test // 인증 없이 보호된 엔드포인트에 접근 시도
	public void testProtectedEndpointWithoutAuth() throws Exception {
		mockMvc.perform(get("/api/users/profile"))
			.andExpect(status().isUnauthorized());
	}

	@Test // 모의 사용자를 이용하여 인증된 상태로 보호된 엔드포인트에 접근
	@WithMockUser(username = "user@example.com")
	public void testProtectedEndpointWithAuth() throws Exception {
		mockMvc.perform(get("/api/users/profile"))
			.andExpect(status().isOk());
	}

	@Test // OAuth2 로그인 성공 테스트
	public void testOAuth2LoginSuccess() throws Exception {
		mockMvc.perform(get("/oauth2/authorization/google"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location",
				Matchers.startsWith("https://accounts.google.com/o/oauth2/auth")));
	}
}
