package team4.backend.security;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;

	public OAuth2LoginSuccessHandler(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		// JWT 토큰 생성
		String jwt = jwtTokenProvider.generateToken(authentication);

		// 클라이언트에 리디렉션할 URL 설정 (기본 URL에 토큰을 쿼리 파라미터로 전달)
		String redirectUrl = "http://localhost:my_page_1?token=" + jwt;
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
