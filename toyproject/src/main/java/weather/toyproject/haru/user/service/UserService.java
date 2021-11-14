package weather.toyproject.haru.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weather.toyproject.haru.user.UserMapper;
import weather.toyproject.haru.user.dao.UserRepository;
import weather.toyproject.haru.user.domain.CustomUserDetails;

@Service
public class UserService {

	UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public CustomUserDetails getUserById(String id, String password) {
		return userRepository.getUserById(id);
	}
}
