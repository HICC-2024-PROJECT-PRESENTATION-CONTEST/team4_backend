package team4.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team4.backend.entity.User;
import team4.backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return org.springframework.security.core.userdetails.User
			.withUsername(user.getEmail())
			.password("")
			.accountExpired(false)
			.accountLocked(false)
			.credentialsExpired(false)
			.disabled(false)
			.build();
	}
}
