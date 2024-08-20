package team4.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	@Autowired
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 이메일을 발송합니다.
	 *
	 * @param to      수신자의 이메일 주소
	 * @param subject 이메일 제목
	 * @param text    이메일 본문 내용
	 * @throws MessagingException 이메일 발송 실패 시 발생
	 */
	public void sendEmail(String to, String subject, String text) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text, true);

		// 이메일 발송
		mailSender.send(message);
	}
}