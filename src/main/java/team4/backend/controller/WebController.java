package team4.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

	// 초기 페이지
	@GetMapping("/")
	public String initialPage() {
		return "initial_page"; // initial_page.html 렌더링
	}

	// 로그인 페이지
	@GetMapping("/login")
	public String loginPage() {
		return "login_page";
	}

	@GetMapping("/my_page_1")
	public String myPage1() {
		return "my_page_1"; // my_page_1.html 파일을 반환
	}

	@GetMapping("/search")
	public String searchResultsPage(@RequestParam(name = "query", required = false, defaultValue = "") String query, Model model) {
		// Here you can pass search results to the view
		model.addAttribute("searchQuery", query);
		return "search_results_page";
	}
}
