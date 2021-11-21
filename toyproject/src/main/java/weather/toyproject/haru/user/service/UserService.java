package weather.toyproject.haru.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import weather.toyproject.haru.user.UserMapper;
import weather.toyproject.haru.user.dao.UserRepository;
import weather.toyproject.haru.user.domain.AuthVO;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;

@Service
public class UserService {

	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	
	public UserVO getUserById(String userId) {
		return userRepository.getUserById(userId);
	}
	
	public List<AuthVO> getMember_Auth(String userId) {
		return userRepository.getMember_Auth(userId);
	}
	
	public int InsertUser(UserVO userVO) {
		userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		return userRepository.InsertUser(userVO);
	}
}
