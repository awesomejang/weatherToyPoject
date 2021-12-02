package weather.toyproject.haru.main;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import weather.toyproject.haru.user.domain.CustomUserDetails;

@Controller
public class indexController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/index.html")
	public String static_index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		String name = "";
		if(principal != null && principal instanceof CustomUserDetails) {
			name = ((CustomUserDetails)principal).getUsername();
		}
		System.out.println("user name =" + name);
		return "index";
	}
}
