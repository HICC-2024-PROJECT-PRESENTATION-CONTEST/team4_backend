package team4.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import team4.backend.entity.Role;
import team4.backend.entity.User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CustomOAuth2User implements OAuth2User {

	private final User user;

	private final Map<String, Object> attributes;

	public CustomOAuth2User(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRole();
		return roles.stream()
			.map(role -> new SimpleGrantedAuthority(role.name()))
			.collect(Collectors.toSet());
	}

	@Override
	public String getName() {
		return user.getEmail();
	}

}
