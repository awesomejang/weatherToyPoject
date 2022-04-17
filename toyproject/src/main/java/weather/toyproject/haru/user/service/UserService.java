package weather.toyproject.haru.user.service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import weather.toyproject.com.ComUtil;
import weather.toyproject.haru.user.dao.UserRepository;
import weather.toyproject.haru.user.domain.AuthVO;
import weather.toyproject.haru.user.domain.GameListDto;
import weather.toyproject.haru.user.domain.UserVO;

@Slf4j
@Service
@PropertySource("classpath:/com/ApiRequestInfo.properties")
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final Environment enviroment;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Environment enviroment) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.enviroment = enviroment;
	}

	
	public UserVO getUserById(String userId) {
		return userRepository.getUserById(userId);
	}
	
	/**
	 * 
	 * @param userId
	 * @return Map<String, String>
	 * 아이디 중복확인의 결과코드, 메세지를 리턴한다.
	 */
	
	public Map<String, String> userIdDupCheck(String userId) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if(ComUtil.StringEmptyCheck(userId)) {			
			resultMap.put("code", "EMPTY");
			resultMap.put("msg", "아이디가 입력되지 않았습니다.");
			return resultMap;
		}
		UserVO userVO = userRepository.getUserById(userId);
		if(userVO != null) {
			resultMap.put("code", "DUP");
			resultMap.put("msg", "이미 사용중인 아이디입니다.");
			return resultMap;
		}
		resultMap.put("code", "OK");
		resultMap.put("msg", "사용 가능한 아이디입니다.");
		return resultMap;
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
		else throw new IllegalStateException("회원가입에 실패했습니다.");
		
		return result;
	}
	
	/**
	 * 입력된 가입신청 항목의 validation확인 후 메세지를 리턴한다.  
	 * @param userVO
	 * @return Map
	 * 
	 */
	public Map<String, String> UserValidateHandling(UserVO userVO, Errors errors) {
		Map<String, String> validatorResult = new HashMap<String, String>();
		
		//==VO에 validation 체크 선언하지 않은 항목확인==//
		
		if(!StringEmptyCheck(userVO.getPassword()) && StringEmptyCheck(userVO.getSecondPassword())) {
			errors.rejectValue("secondPassword","secondPasswordEmpty", "비밀번호 확인란을 입력해주세요"); 
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
	
	public List<GameListDto> selectGameList_admin() {
		return userRepository.selectGameList_admin();
	}
	
	public boolean StringEmptyCheck(String str) {
		return str == null || str.trim().isEmpty();
	}
	
}
