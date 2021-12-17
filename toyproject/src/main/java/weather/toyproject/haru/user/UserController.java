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
		String msg;
		Map<String, String> validatorResult = userService.UserValidateHandling(userVO, errors);
		
		if(validatorResult.isEmpty() != true) {
			for(String key : validatorResult.keySet()) {
				redirectAttributes.addAttribute(key, validatorResult.get(key));
			}
			//return "redirect:user/userRegistForm";
			return "redirect:/user/new";
		}
		boolean result = userService.InsertUser(userVO);
		
		if(result != true) {
			msg = "회원가입에 실패했습니다.";
			return viewPath;
		}
		return "user/userRegistForm";
	}
}
