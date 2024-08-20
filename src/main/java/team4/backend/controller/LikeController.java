package team4.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team4.backend.dto.ProductResponseDto;
import team4.backend.entity.Product;
import team4.backend.response.ApiResponse;
import team4.backend.service.LikeService;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	// 특정 상품을 찜하기
	@PostMapping("/{userId}/product/{productId}")
	public ResponseEntity<ApiResponse<?>> likeProduct(@PathVariable Long userId, @PathVariable Long productId) {
		likeService.likeProduct(userId, productId);
		return ResponseEntity.ok(ApiResponse.ok("상품 찜하기 성공", null));
	}

	// 특정 상품 찜 해제
	@DeleteMapping("/{userId}/product/{productId}")
	public ResponseEntity<ApiResponse<?>> unlikeProduct(@PathVariable Long userId, @PathVariable Long productId) {
		likeService.unlikeProduct(userId, productId);
		return ResponseEntity.ok(ApiResponse.ok("상품 찜 해제 성공", null));
	}

	// 사용자가 찜한 상품 조회
	@GetMapping("/{userId}")
	public ResponseEntity<?> getLikedProductsByUser(@PathVariable Long userId) {
		List<Product> likedProducts = likeService.getLikedProductsByUser(userId);
		List<ProductResponseDto> productDtos = likedProducts.stream()
			.map(ProductResponseDto::new)
			.collect(Collectors.toList());
		return ResponseEntity.ok(ApiResponse.ok("찜한 상품 조회 성공", productDtos));
	}
}