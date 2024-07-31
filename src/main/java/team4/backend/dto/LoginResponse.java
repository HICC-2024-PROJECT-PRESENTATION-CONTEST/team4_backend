package team4.backend.dto;

import lombok.Data;

@Data
public class LoginResponse {
	private String token;

	public LoginResponse(String token) {
		this.token = token;
	}
}
