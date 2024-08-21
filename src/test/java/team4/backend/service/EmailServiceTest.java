package team4.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceTest {

	@Mock
	private JavaMailSender mailSender;

	@InjectMocks
	private EmailService emailService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void sendEmail_ShouldSendEmail() throws MessagingException {

		// JavaMailSender에서 MimeMessage 객체를 생성하도록 설정
		MimeMessage mimeMessage = mock(MimeMessage.class);
		when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

		// EmailService의 sendEmail 메서드 호출
		emailService.sendEmail("test@example.com", "Subject", "Text");

		// mailSender의 send 메서드가 한 번 호출되었는지 검증
		verify(mailSender, times(1)).send(any(MimeMessage.class));
	}
}
