package team4.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import team4.backend.entity.User;
import team4.backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		// 사용자의 역할(Role)을 Spring Security에서 사용 가능한 권한 객체(SimpleGrantedAuthority)로 변환하여 리스트에 저장
		List<SimpleGrantedAuthority> authorities = user.getRole().stream()
			.map(role -> new SimpleGrantedAuthority(role.name()))
			.collect(Collectors.toList());

		return org.springframework.security.core.userdetails.User
			.withUsername(user.getEmail())
			.password("")
			.authorities(authorities)
			.accountExpired(false)
			.accountLocked(false)
			.credentialsExpired(false)
			.disabled(false)
			.build();
	}
}
