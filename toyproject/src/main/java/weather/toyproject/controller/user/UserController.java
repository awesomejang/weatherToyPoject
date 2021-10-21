package weather.toyproject.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/weblogin/create_account")
	public String createAccount() {
		return "/user/join/user_join";
	}
}

