package team4.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import team4.backend.entity.Like;
import team4.backend.entity.Product;
import team4.backend.entity.User;
import team4.backend.repository.LikeRepository;
import team4.backend.repository.ProductRepository;
import team4.backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final EmailService emailService;

	public LikeService(LikeRepository likeRepository, UserRepository userRepository,
		ProductRepository productRepository, EmailService emailService) {
		this.likeRepository = likeRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.emailService = emailService;
	}

	// 특정 사용자가 특정 상품을 찜하기
	@Transactional
	public void likeProduct(Long userId, Long productId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new RuntimeException("Product not found"));

		Optional<Like> existingLike = likeRepository.findByUserAndProduct(user, product);
		if (existingLike.isPresent()) {
			throw new RuntimeException("Product already liked by user");
		}

		Like like = Like.builder()
			.user(user)
			.product(product)
			.initialPrice(product.getPrice())
			.build();

		likeRepository.save(like);
	}

	// 특정 사용자가 특정 상품의 찜을 해제
	@Transactional
	public void unlikeProduct(Long userId, Long productId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new RuntimeException("Product not found"));

		Like like = likeRepository.findByUserAndProduct(user, product)
			.orElseThrow(() -> new RuntimeException("Like not found"));

		likeRepository.delete(like);
	}

	// 특정 사용자가 찜한 상품 목록 조회
	public List<Product> getLikedProductsByUser(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));

		List<Like> likes = likeRepository.findAllByUser(user);
		return likes.stream()
			.map(Like::getProduct)
			.collect(Collectors.toList());
	}

	// 찜한 상품의 가격 하락 시 사용자에게 이메일 알림
	@Transactional
	public void notifyPriceDrop(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new RuntimeException("Product not found"));

		List<Like> likes = likeRepository.findAllByProduct(product);

		for (Like like : likes) {
			if (product.getPrice() < like.getInitialPrice()) {
				boolean shouldSendNotification = true;
				if (like.getLastNotificationTime() != null) {
					LocalDateTime now = LocalDateTime.now();
					shouldSendNotification = like.getLastNotificationTime().plusDays(3).isBefore(now);
				}

				if (shouldSendNotification) {
					sendPriceDropEmail(like.getUser(), product, like.getInitialPrice());
					like.setLastNotificationTime(LocalDateTime.now());
					likeRepository.save(like);
				}
			}
		}
	}

	private void sendPriceDropEmail(User user, Product product, int initialPrice) {
		String subject = "찜한 상품의 가격이 하락했습니다!";
		String text = String.format(
			"안녕하세요 %s님,\n\n" +
				"찜하신 상품 '%s'의 가격이 하락했습니다.\n\n" +
				"상품 상세페이지: %s\n" +
				"찜 당시 가격: %d원\n" +
				"현재 가격: %d원\n\n" +
				"지금 확인해보세요!",
			user.getEmail(),
			product.getProductName(),
			product.getProductURL(),
			initialPrice,
			product.getPrice()
		);

		try {
			emailService.sendEmail(user.getEmail(), subject, text);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
