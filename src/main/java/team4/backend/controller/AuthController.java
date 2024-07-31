package team4.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import team4.backend.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		// 로그아웃 로직 구현 (토큰 무효화 등)
		return ResponseEntity.ok("Successfully logged out");
	}
}
