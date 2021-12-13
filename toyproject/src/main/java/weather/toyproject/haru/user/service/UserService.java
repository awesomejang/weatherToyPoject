package weather.toyproject.haru.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

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
	
	/**
	 * 
	 * @param userVO
	 * @return boolean
	 * 회원가입 결과를 리턴한다.회원가입결과가 1 이상일 경우 true, 아닐경우 false
	 */
	//@Transactional //선언안해주니까 예외 발생해도 데이터 들어간다;
	public boolean InsertUser(UserVO userVO) {
		boolean result = false;
		userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		
		int resultCount = userRepository.InsertUser(userVO);
		if(resultCount > 0) result = true;  
		return result;
	}
	
	//== 사용자정보 validation을 위한 메소드 ==// 	
	public Map<String, String> validateHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<String, String>();
		
		for(FieldError error : errors.getFieldErrors()) {
			String validkeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validkeyName, error.getDefaultMessage());
		}
		return validatorResult;
	}
}
