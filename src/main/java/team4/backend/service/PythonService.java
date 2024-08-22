package team4.backend.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PythonService {

	private final RestTemplate restTemplate = new RestTemplate();

	// 하루에 한 번 크롤링 실행
	@Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
	public void performCrawl() {
		String url = "http://localhost:5000/crawl";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		System.out.println("Crawling result: " + response.getBody());
	}

	// 유사한 제품 추천 요청
	public String getRecommendations(String productName) {
		String url = "http://localhost:5000/recommend";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>("{\"product_name\": \"" + productName + "\"}", headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		return response.getBody();
	}
}
