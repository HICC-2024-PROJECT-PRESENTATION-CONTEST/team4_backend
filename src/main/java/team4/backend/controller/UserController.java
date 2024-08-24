package team4.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import team4.backend.dto.UserDto;
import team4.backend.entity.User;
import team4.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// 사용자 정보 조회
	@GetMapping("/profile")
	public ResponseEntity<UserDto> getUserProfile(Authentication authentication) {
		String email = authentication.getName();
		User user = userService.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("User not found"));
		return ResponseEntity.ok(new UserDto(user.getUserId(), user.getEmail()));
	}

	// 사용자 탈퇴
	@DeleteMapping("/profile")
	public ResponseEntity<String> deleteUserProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
		}

		String email = authentication.getName();
		userService.deleteByEmail(email);
		return ResponseEntity.ok("User deleted successfully");
	}

	// 로그인된 사용자의 이메일 반환
	@GetMapping("/email")
	public ResponseEntity<UserDto> getUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userService.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("User not found"));
		return ResponseEntity.ok(new UserDto(user.getUserId(), user.getEmail()));
	}

	// 사용자 ID를 반환하는 엔드포인트
	@GetMapping("/id")
	public ResponseEntity<UserDto> getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userService.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("User not found"));
		return ResponseEntity.ok(new UserDto(user.getUserId(), user.getEmail()));
	}
}
