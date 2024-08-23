package team4.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

	private final SecretKey jwtSecret;

	@Value("${spring.security.jwt.expiration-time}")
	private int jwtExpirationInMs;

	public JwtTokenProvider() {
		this.jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);  // 자동으로 강력한 키를 생성
	}

	// JWT 토큰 생성
	public String generateToken(Authentication authentication) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		// JWT 토큰 생성 및 반환
		return Jwts.builder()
			.setSubject(authentication.getName())
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(jwtSecret, SignatureAlgorithm.HS512)
			.compact();
	}

	// JWT 토큰에서 사용자 이메일 추출
	public String getUserEmailFromJWT(String token) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(jwtSecret)
			.build()
			.parseClaimsJws(token)
			.getBody();

		return claims.getSubject();
	}

	// JWT 토큰 검증
	public boolean validateToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
			return true;
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
	}
}