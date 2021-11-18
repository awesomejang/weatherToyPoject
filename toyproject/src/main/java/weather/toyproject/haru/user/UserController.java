package weather.toyproject.haru.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import weather.toyproject.haru.user.domain.UserVO;
import weather.toyproject.haru.user.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/user")
	public String awta() {
		return "user/join/user_join";
	}
	
	@GetMapping("/login")
	public String loginProcess() {
		UserVO userVO = userService.getUserTest();
		System.out.println("userId = " + userVO.getUserId());
		//System.out.println("userId = " + userVO.getAuthList().get(0));
		return "user/join/loginPage";
	}

}
