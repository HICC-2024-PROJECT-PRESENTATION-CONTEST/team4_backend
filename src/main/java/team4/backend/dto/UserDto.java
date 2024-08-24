package team4.backend.dto;

public class UserDto {

	private Long id;
	private String email;

	public UserDto() {
	}

	public UserDto(Long id, String email) {
		this.id = id;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}
}
