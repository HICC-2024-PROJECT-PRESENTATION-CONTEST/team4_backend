package team4.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	// 로그아웃 요청 처리
	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		// 클라이언트에서 Jwt 삭제 필요
		return ResponseEntity.ok("Successfully logged out");
	}

	// 공개 엔드포인트
	@GetMapping("/public")
	public String publicEndpoint() {
		return "Public endpoint";
	}
}
