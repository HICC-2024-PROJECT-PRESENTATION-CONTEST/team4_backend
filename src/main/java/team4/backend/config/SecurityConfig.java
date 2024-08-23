package team4.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import team4.backend.security.JwtAuthenticationEntryPoint;
import team4.backend.security.JwtAuthenticationFilter;
import team4.backend.security.JwtTokenProvider;
import team4.backend.security.OAuth2LoginSuccessHandler;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.exceptionHandling(exceptionHandling ->
				exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			)
			.sessionManagement(sessionManagement ->
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authorizeHttpRequests(authorize ->
				authorize
					.anyRequest().permitAll()
			)
			.oauth2Login(oauth2 -> oauth2
				.successHandler(oAuth2LoginSuccessHandler())
				.failureUrl("/loginFailure")
				.clientRegistrationRepository(clientRegistrationRepository)
				.authorizedClientService(authorizedClientService)
			);

		// Add the JWT token filter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
	}

	@Bean
	public OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler() {
		return new OAuth2LoginSuccessHandler(jwtTokenProvider);
	}

	// CORS 설정
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setExposedHeaders(Collections.singletonList("Authorization"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// PasswordEncoder 빈 정의 (BCrypt 암호화 방식 사용)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// AuthenticationManager
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}
}
