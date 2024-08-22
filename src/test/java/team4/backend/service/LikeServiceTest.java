package team4.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team4.backend.entity.Like;
import team4.backend.entity.Product;
import team4.backend.entity.User;
import team4.backend.repository.LikeRepository;
import team4.backend.repository.ProductRepository;
import team4.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LikeServiceTest {

	@InjectMocks
	private LikeService likeService;

	@Mock
	private LikeRepository likeRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ProductRepository productRepository;

	private User user;
	private Product product;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		user = User.builder().email("test@example.com").build();
		product = Product.builder().productName("Test Product").build();
	}

	@Test
	public void testLikeProduct() {

		// Mock 설정: 사용자와 상품이 존재하고, 해당 사용자가 이미 상품을 찜하지 않은 경우
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
		when(likeRepository.findByUserAndProduct(any(User.class), any(Product.class))).thenReturn(Optional.empty());

		// LikeService의 likeProduct 메서드 호출
		likeService.likeProduct(1L, 1L);

		// likeRepository의 save 메서드가 한 번 호출되었는지 검증
		verify(likeRepository, times(1)).save(any(Like.class));
	}

	@Test
	public void testUnlikeProduct() {

		// Mock 설정: 사용자와 상품이 존재하고, 해당 사용자가 이미 상품을 찜한 경우
		Like like = Like.builder().user(user).product(product).build();
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
		when(likeRepository.findByUserAndProduct(any(User.class), any(Product.class))).thenReturn(Optional.of(like));

		// LikeService의 unlikeProduct 메서드 호출
		likeService.unlikeProduct(1L, 1L);

		// likeRepository의 delete 메서드가 한 번 호출되었는지 검증
		verify(likeRepository, times(1)).delete(like);
	}

	@Test
	public void testGetLikedProductsByUser() {

		// Mock 설정: 사용자가 특정 상품을 찜한 경우
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(likeRepository.findAllByUser(any(User.class))).thenReturn(List.of(Like.builder().user(user).product(product).build()));

		// LikeService의 getLikedProductsByUser 메서드 호출
		List<Product> likedProducts = likeService.getLikedProductsByUser(1L);

		// 찜한 상품의 수와 이름이 예상대로인지 검증
		assertEquals(1, likedProducts.size());
		assertEquals("Test Product", likedProducts.get(0).getProductName());
	}
}