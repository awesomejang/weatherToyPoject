package weather.toyproject.haru.user.service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import weather.toyproject.haru.user.UserMapper;
import weather.toyproject.haru.user.dao.UserRepository;
import weather.toyproject.haru.user.domain.AuthVO;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;

@Service
@Slf4j
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
	 * @ 회원가입 결과를 리턴한다.회원가입결과가 1 이상일 경우 true, 아닐경우 false
	 */
	//@Transactional //선언안해주니까 예외 발생해도 데이터 들어간다;
	public boolean InsertUser(UserVO userVO) {
		
		boolean result = false;
		userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		
		int resultCount = userRepository.InsertUser(userVO);
		log.info("InserUser-resultCount = {}", resultCount);
		
		if(resultCount > 0) result = true;
		return result;
	}
	
	/**
	 * 사용자정보 validation을 위한 메소드
	 * @param userVO
	 * @return boolean
	 * 
	 */
	public Map<String, String> UserValidateHandling(UserVO userVO, Errors errors) {
		Map<String, String> validatorResult = new HashMap<String, String>();
		
		//==VO에 validation 체크 선언하지 않은 항목확인==//
		
		if(!StringEmptyCheck(userVO.getPassword()) && StringEmptyCheck(userVO.getSecondPassword())) {
			errors.rejectValue("secondPassword","nomatch", "비밀번호 확인란을 입력해주세요"); 
		}
		
		if(!Objects.equals(userVO.getPassword(), userVO.getSecondPassword())) {
			errors.rejectValue("secondPassword","passwordNoMatch", "비밀번호가 동일하지 않습니다."); 
		}
		
		//=====================================//
		
		for(FieldError error : errors.getFieldErrors()) { // 캡슐화된 Error객체
			String validkeyName = String.format("valid_%s", error.getField()); //공통적인 메시지 처리에 유리
			log.info("validation check = {}", validkeyName);
			validatorResult.put(validkeyName, error.getDefaultMessage()); //VO에 선언한 메세지
		}
		return validatorResult;
	}
	
	
	public void addValidAttribute(Map<String, String> validatorResult, RedirectAttributes redirectAttributes) {
		for(String key : validatorResult.keySet()) {
			//addattribute할경우 url파라미터로 전달되는데 html에서 받을때 url파라미터에서 가져오는게 아니기때문에 보이지않는다.
			// redirectAttribute.addAttribute -> get/url파라미터에 전달 / addFlashAttribute -> POST
			redirectAttributes.addFlashAttribute(key, validatorResult.get(key)); // Object 전달할때 사용(일반 문자열도 가능한다. 큰 특징은 URL에 노출이 안된다는점)
		}
	}
	
	public boolean StringEmptyCheck(String str) {
		return str == null || str.trim().isEmpty();
	}
	
}
