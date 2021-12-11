package weather.toyproject.haru.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
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
	public String UserRegistForm(HttpServletRequest request,UserVO userVO, HttpServletResponse response, Model model) {
		return "user/userRegistForm";
	}
	
	@PostMapping("/user/new") 
	public String UserRegistProcess(@ModelAttribute("userVO") UserVO userVO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		String viewPath = "";
		//로그인 성공  -> main페이지로
		//로그인 실패 OR validation -> 메세지와 함께 로그인페이지로
		//ObjectUtils
		Map<String, String> errors = new HashMap<String, String>();
		if(ObjectUtils.isEmpty(userVO.getUserId())) {
			errors.put("id", "아이디를 입력해주세요");
		}
		
		if(errors.isEmpty() == false) {
			// String == addAttribute, Object(VO, Map,List) == addFlashAttribute
			log.info("Errors = {}", "아이디 입력이 누락되었습니다.");
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:/user/new";
		}
		
		boolean result = userService.InsertUser(userVO);
		if(result) {
			viewPath = "/";
			return viewPath;
		}
		return "user/userRegistForm";
	}
}
