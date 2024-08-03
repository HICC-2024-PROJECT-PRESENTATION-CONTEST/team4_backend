package team4.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

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

	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String kakaoClientId;

	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String kakaoClientSecret;

	@Value("${spring.security.oauth2.client.registration.kakao.client-authentication-method}")
	private String kakaoClientAuthenticationMethod;

	@Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
	private String kakaoAuthorizationGrantType;

	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String kakaoRedirectUri;

	@Value("${spring.security.oauth2.client.registration.kakao.scope}")
	private String kakaoScope;

	@Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
	private String kakaoAuthorizationUri;

	@Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
	private String kakaoTokenUri;

	@Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
	private String kakaoUserInfoUri;

	@Value("${spring.security.oauth2.client.provider.kakao.user-name-attribute}")
	private String kakaoUserNameAttribute;

	@Value("${spring.security.oauth2.client.registration.instagram.client-id}")
	private String instagramClientId;

	@Value("${spring.security.oauth2.client.registration.instagram.client-secret}")
	private String instagramClientSecret;

	@Value("${spring.security.oauth2.client.registration.instagram.client-authentication-method}")
	private String instagramClientAuthenticationMethod;

	@Value("${spring.security.oauth2.client.registration.instagram.authorization-grant-type}")
	private String instagramAuthorizationGrantType;

	@Value("${spring.security.oauth2.client.registration.instagram.redirect-uri}")
	private String instagramRedirectUri;

	@Value("${spring.security.oauth2.client.registration.instagram.scope}")
	private String instagramScope;

	@Value("${spring.security.oauth2.client.provider.instagram.authorization-uri}")
	private String instagramAuthorizationUri;

	@Value("${spring.security.oauth2.client.provider.instagram.token-uri}")
	private String instagramTokenUri;

	@Value("${spring.security.oauth2.client.provider.instagram.user-info-uri}")
	private String instagramUserInfoUri;

	@Value("${spring.security.oauth2.client.provider.instagram.user-name-attribute}")
	private String instagramUserNameAttribute;

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

		ClientRegistration kakaoClientRegistration = ClientRegistration.withRegistrationId("kakao")
			.clientId(kakaoClientId)
			.clientSecret(kakaoClientSecret)
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.redirectUri(kakaoRedirectUri)
			.scope(kakaoScope.split(","))
			.authorizationUri(kakaoAuthorizationUri)
			.tokenUri(kakaoTokenUri)
			.userInfoUri(kakaoUserInfoUri)
			.userNameAttributeName(kakaoUserNameAttribute)
			.clientName("Kakao")
			.build();

		ClientRegistration instagramClientRegistration = ClientRegistration.withRegistrationId("instagram")
			.clientId(instagramClientId)
			.clientSecret(instagramClientSecret)
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.redirectUri(instagramRedirectUri)
			.scope(instagramScope.split(","))
			.authorizationUri(instagramAuthorizationUri)
			.tokenUri(instagramTokenUri)
			.userInfoUri(instagramUserInfoUri)
			.userNameAttributeName(instagramUserNameAttribute)
			.clientName("Instagram")
			.build();

		return new InMemoryClientRegistrationRepository(
			googleClientRegistration, kakaoClientRegistration, instagramClientRegistration);
	}
}
