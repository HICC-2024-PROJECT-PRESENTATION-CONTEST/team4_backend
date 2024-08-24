package team4.backend.security;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import team4.backend.entity.User;
import team4.backend.service.UserService;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;  // Add UserService as a dependency

	public OAuth2LoginSuccessHandler(JwtTokenProvider jwtTokenProvider, UserService userService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;  // Inject UserService
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String email = authentication.getName();

		// 사용자 정보가 이미 존재하는지 확인
		Optional<User> existingUser = userService.findByEmail(email);
		if (existingUser.isEmpty()) {
			// 존재하지 않는다면 새 사용자 생성 및 저장
			User newUser = User.builder()
				.email(email)
				.provider("google")
				.providerId(authentication.getName())
				.build();
			userService.save(newUser);
		}

		// JWT 토큰 생성
		String jwt = jwtTokenProvider.generateToken(authentication);

		// 클라이언트에 리디렉션할 URL 설정 (기본 URL에 토큰을 쿼리 파라미터로 전달)
		String redirectUrl = "http://localhost:8080/my_page_1?token=" + jwt;
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
