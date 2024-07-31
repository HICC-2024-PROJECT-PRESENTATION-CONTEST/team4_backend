package team4.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import team4.backend.entity.User;
import team4.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// 사용자 정보 조회
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(Authentication authentication) {
		String email = authentication.getName();
		User user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		return ResponseEntity.ok(user);
	}

	// 사용자 탈퇴
	@DeleteMapping("/profile")
	public ResponseEntity<String> deleteUserProfile(Authentication authentication) {
		String email = authentication.getName();
		userService.deleteByEmail(email);
		return ResponseEntity.ok("User deleted successfully");
	}
}
