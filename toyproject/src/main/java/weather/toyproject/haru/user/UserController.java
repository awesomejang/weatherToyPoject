package weather.toyproject.haru.user;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;
import weather.toyproject.haru.user.service.UserService;

@PropertySource(value = "classpath:/com/message.properties", encoding = "UTF-8")
@Slf4j
@Controller
public class UserController {
	
	//@Value("#{ApiResource['weather.api.url']}")
	//private String url;
	
	private Environment environment;
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService, Environment environment) {
		this.userService = userService;
		this.environment = environment;
	}
	
	@GetMapping("/login")
	public String loginProcess(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "user/loginPage";
	}
	
	@GetMapping("/user/new") 
	public String userNew(@ModelAttribute UserVO userVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "user/userRegistForm";
	}
	
	@PostMapping("/user/new")
	public String UserRegistProcess(@ModelAttribute("userVO") @Valid UserVO userVO, Errors errors, HttpServletRequest request
			, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		
		//@valid : 클라이언트의 입력 데이터가 dto클래스로 캡슐화되어 넘어올 때, 유효성을 체크하라는 어노테이션
		//Errors : vo에 binding된 필드의 유효성 검사 오류에 대한 정보를 저장하고 노출합니다.
		Map<String, String> validatorResult = userService.UserValidateHandling(userVO, errors);
		
		if(validatorResult.isEmpty() != true) {
			userService.addValidAttribute(validatorResult, redirectAttributes);
			
			redirectAttributes.addFlashAttribute("userVO", userVO);
			return "redirect:/user/new";
		}
		
		boolean result = userService.InsertUser(userVO);
		if(result != true) {
			log.info("userRegist_fail = {}", userService.InsertUser(userVO));
			 
			redirectAttributes.addFlashAttribute("userRegistMsg", environment.getProperty("user.regist.fail.msg"));
			redirectAttributes.addFlashAttribute("userVO", userVO);
			return "redirect:/user/new";
		}
		
		redirectAttributes.addFlashAttribute("userRegistMsg", environment.getProperty("user.regist.success.msg"));
		return "redirect:/";
	}
	
	@GetMapping("/userId/DupCheck/{userId}")
	@ResponseBody
	public String userIdDupCheck(@PathVariable String userId) {
		return userId;
	}
}
