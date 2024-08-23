package team4.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2ClientConfig {

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googleClientId;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String googleClientSecret;

	@Value("${spring.security.oauth2.client.registration.google.scope}")
	private String googleScope;

	@Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
	private String googleAuthorizationUri;

	@Value("${spring.security.oauth2.client.provider.google.token-uri}")
	private String googleTokenUri;

	@Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
	private String googleUserInfoUri;

	@Value("${spring.security.oauth2.client.provider.google.jwk-set-uri}")
	private String googleJwkSetUri;

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		ClientRegistration googleClientRegistration = ClientRegistration.withRegistrationId("google")
			.clientId(googleClientId)
			.clientSecret(googleClientSecret)
			.scope(googleScope.split(","))
			.authorizationUri(googleAuthorizationUri)
			.tokenUri(googleTokenUri)
			.userInfoUri(googleUserInfoUri)
			.jwkSetUri(googleJwkSetUri)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.redirectUri("{baseUrl}/login/oauth2/code/google")
			.clientName("Google")
			.build();

		return new InMemoryClientRegistrationRepository(
			googleClientRegistration);
	}
}
