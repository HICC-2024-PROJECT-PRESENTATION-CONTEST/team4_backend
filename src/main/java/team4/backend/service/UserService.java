package team4.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team4.backend.entity.User;
import team4.backend.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	public User save(User user) {
		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void deleteByEmail(String email) {
		userRepository.deleteByEmail(email);
	}

	public Authentication authenticateUser(String email, String password) throws AuthenticationException {
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		if (userDetails == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities()));
		return authentication;
	}
}
