package team4.backend.dto;

public class UserDto {

	private String email;

	public UserDto() {
	}

	public UserDto(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
