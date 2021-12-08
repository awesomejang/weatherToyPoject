package weather.toyproject.haru.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;
import weather.toyproject.haru.user.service.UserService;

@Controller
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
 		//System.out.println("redirect = " + request.getAttribute("message").toString());
		return "user/loginPage";
	}
	
	@GetMapping("/user/new") 
	public String UserRegistForm() {
		return "user/userRegistForm";
	}
	
	@PostMapping("/user/new") 
	public String UserRegistProcess(@ModelAttribute UserVO userVO, HttpServletRequest request, HttpServletResponse response) {
		//로그인 성공  -> main페이지로
		//로그인 실패 OR validation -> 메세지와 함께 로그인페이지로 
		userService.InsertUser(userVO);
		System.out.println("user id = " + userVO.getUserId());
		return "user/userRegistForm";
	}
}
