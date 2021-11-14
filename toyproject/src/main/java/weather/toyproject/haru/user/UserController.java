package weather.toyproject.haru.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/user")
	public String awta() {
		return "user/join/user_join";
	}
	
	@GetMapping("/login")
	public String loginProcess() {
		return "user/join/loginPage";
	}

}
