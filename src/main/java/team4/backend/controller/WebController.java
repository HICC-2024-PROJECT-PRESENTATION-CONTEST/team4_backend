package team4.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import team4.backend.dto.ProductResponseDto;
import team4.backend.service.ProductService;
import team4.backend.service.PythonService;

import java.util.List;

@Controller
public class WebController {

	private final ProductService productService;
	private final PythonService pythonService;

	public WebController(ProductService productService, PythonService pythonService) {
		this.productService = productService;
		this.pythonService = pythonService;
	}

	// 초기 페이지
	@GetMapping("/initial")
	public String initialPage() {
		return "initial_page"; // initial_page.html을 렌더링
	}

	// 로그인 페이지
	@GetMapping("/login")
	public String loginPage() {
		return "login_page"; // login_page.html을 렌더링
	}

	// 검색 결과 페이지
	@GetMapping("/search")
	public String searchResultsPage(@RequestParam(name = "query", required = false, defaultValue = "") String query, Model model) {
		List<ProductResponseDto> searchResults = productService.searchProducts(query);

		// 모델에 데이터 추가 (Thymeleaf 템플릿에서 사용)
		model.addAttribute("searchQuery", query);
		model.addAttribute("searchResults", searchResults);

		// search_results_page.html을 렌더링
		return "search_results_page";
	}

	// 특정 제품의 추천 상품을 가져오는 엔드포인트
	@GetMapping("/product/{productId}/recommendations")
	public String getRecommendations(@PathVariable Long productId, Model model) {
		// 제품 ID로 제품명 조회
		String productName = productService.getProductNameById(productId);

		// Python 서비스로부터 추천 결과 받기
		String recommendations = pythonService.getRecommendations(productName);

		// 모델에 데이터 추가 (Thymeleaf 템플릿에서 사용)
		model.addAttribute("productName", productName);
		model.addAttribute("recommendations", recommendations);

		// 추천 결과 페이지로 이동 (추천 결과를 보여줄 템플릿이 필요)
		return "recommendations_page";
	}
}
