package weather.toyproject.haru.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;
import weather.toyproject.haru.user.service.UserService;

@Controller
@Slf4j
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/user")
	public String awta() {
		return "user/join/user_join";
	}
	
	@GetMapping("/login")
	public String loginProcess(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "user/loginPage";
	}
	
	@GetMapping("/user/new") 
	public String UserRegistForm(HttpServletRequest request, UserVO userVO, HttpServletResponse response, Model model) {
		return "user/userRegistForm";
	}
	
	
	@PostMapping("/user/new")
	public String UserRegistProcess(@ModelAttribute("userVO") @Valid UserVO userVO, Errors errors, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		//@valid : 클라이언트의 입력 데이터가 dto클래스로 캡슐화되어 넘어올 때, 유효성을 체크하라는 어노테이션
		//Errors : vo에 binding된 필드의 유효성 검사 오류에 대한 정보를 저장하고 노출합니다.
		String viewPath = "";
		/**
		Map<String, String> errors = new HashMap<String, String>();
		if(ObjectUtils.isEmpty(userVO.getUserId())) {
			errors.put("id", "아이디를 입력해주세요");
		}
		*/
		if(!userVO.getPassword().equals(userVO.getSecondPassword())) {
			errors.rejectValue("secondPassword","nomatch", "비밀번호가 동일하지 않습니다."); // Error추가
		}
		if(errors.hasErrors()) {
			//== 유효성 검사 통과 못한 필드와 메세지 핸들링 
			Map<String, String> validatorResult = userService.validateHandling(errors);
			for(String key : validatorResult.keySet()) {
				log.info("Errors = {}", validatorResult.get(key));
				model.addAttribute(key, validatorResult.get(key));
			}
			//redirectAttributes.addFlashAttribute("errors", errors);
			return "user/userRegistForm";
		}
		
		boolean result = userService.InsertUser(userVO);
		if(result) {
			viewPath = "index.html";
			return viewPath;
		}
		return "user/userRegistForm";
	}
}
