package weather.toyproject.haru.main;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import weather.toyproject.haru.user.domain.CustomUserDetails;

@Controller
public class IndexController {
	
	@RequestMapping(value={"/", "/index.html"})
	public String index() {
		return "index";
	}
	
	/**
	@RequestMapping("/index.html")
	public String static_index(HttpServletRequest request) {
		return "index";
	}
	*/
}
