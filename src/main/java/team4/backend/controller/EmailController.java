package team4.backend.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team4.backend.response.ApiResponse;
import team4.backend.service.EmailService;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailService;

	@PostMapping("/send")
	public ResponseEntity<ApiResponse<?>> sendEmail(
		@RequestParam String to,
		@RequestParam String subject,
		@RequestParam String text) {
		try {
			emailService.sendEmail(to, subject, text);
			return ResponseEntity.ok(ApiResponse.ok("이메일 발송 성공", null));
		} catch (MessagingException e) {
			return ResponseEntity.status(500).body(ApiResponse.fail("이메일 발송 실패", e.getMessage()));
		}
	}
}
