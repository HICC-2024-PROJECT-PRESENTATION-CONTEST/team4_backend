package team4.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import team4.backend.entity.User;
import team4.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(false); // 이메일 인증 전까지는 계정 비활성화
		User savedUser = userService.save(user);
		return ResponseEntity.ok(savedUser);
	}

	@GetMapping("/confirm")
	public ResponseEntity<String> confirmUser(@RequestParam String token) {
		Optional<User> userOptional = userService.findByVerificationToken(token);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getTokenExpirationTime() > System.currentTimeMillis()) {
				userService.enableUser(user);
				return ResponseEntity.ok("User confirmed successfully");
			} else {
				return ResponseEntity.badRequest().body("Token expired");
			}
		} else {
			return ResponseEntity.badRequest().body("Invalid token");
		}
	}
}
