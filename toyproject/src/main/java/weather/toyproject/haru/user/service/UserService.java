package weather.toyproject.haru.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weather.toyproject.haru.user.UserMapper;
import weather.toyproject.haru.user.dao.UserRepository;
import weather.toyproject.haru.user.domain.AuthVO;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;

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
	
	@Transactional //선언안해주니까 예외 발생해도 데이터 들어간다;; 
	public int InsertUser(UserVO userVO) {
		userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		
		return userRepository.InsertUser(userVO);
	}
}
