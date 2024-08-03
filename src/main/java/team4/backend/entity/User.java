package team4.backend.entity;

import java.util.Set;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Role> role;

	@Column(name = "provider")
	private String provider; // OAuth2 제공자 이름

	@Column(name = "provider_id")
	private String providerId; // OAuth2 사용자 ID

	// 테스트용 생성자
	public User(String email, Set<Role> role) {
		this.email = email;
		this.role = role;
	}
}
