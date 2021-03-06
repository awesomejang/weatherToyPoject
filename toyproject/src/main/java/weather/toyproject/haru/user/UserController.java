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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.GameListDto;
import weather.toyproject.haru.user.domain.UserVO;
import weather.toyproject.haru.user.domain.form.UserUpdateForm;
import weather.toyproject.haru.user.service.UserService;

@PropertySource(value = "classpath:/com/message.properties", encoding = "UTF-8")
@Slf4j
@Controller
public class UserController {
	
	//@Value("#{ApiResource['weather.api.url']}")
	//private String url;
	
	//private final Environment environment;
	private Environment environment;
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService, Environment environment) {
		this.userService = userService;
		this.environment = environment;
	}
	
	@RequestMapping("/login")
	public String loginProcess(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "user/loginPage";
	}
	
	@GetMapping("/user/new") //????????? model??? ????????? ?????? ??????????????? ????????? ????????? ??????... 
	public String userNew(@ModelAttribute UserVO userVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "user/userRegistForm";
	}
	
	@PostMapping("/user/new")
	public String UserRegistProcess(@ModelAttribute @Valid UserVO userVO, BindingResult bindingResult
			                       ,HttpServletRequest request, @RequestParam("userIdDupCheck") String userIdDupResult
			                       , HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		
		//@valid : ?????????????????? ?????? ???????????? dto???????????? ??????????????? ????????? ???, ???????????? ??????????????? ???????????????
		//Errors : vo??? binding??? ????????? ????????? ?????? ????????? ?????? ????????? ???????????? ???????????????.
		//Map<String, String> validatorResult = userService.UserValidateHandling(userVO, errors);
		if(!bindingResult.hasFieldErrors("password") && !bindingResult.hasFieldErrors("secondPassword") && !userVO.getPassword().equals(userVO.getSecondPassword())) {
			bindingResult.rejectValue("secondPassword", "Equal",null);
		}
		
		if(bindingResult.hasErrors()) {
			log.info("error = {}", bindingResult);
			return "user/userRegistForm";
		}
		//redirectAttributes.addFlashAttribute("userVO", userVO);
		
		/**
		if(validatorResult.isEmpty() != true) {
			userService.addValidAttribute(validatorResult, redirectAttributes);
			//redirectAttributes.addFlashAttribute("userVO", userVO);
			return "redirect:/user/new";
		}
		*/
		if(userIdDupResult.equals("N")) {
			redirectAttributes.addFlashAttribute("userRegistMsg", "????????? ??????????????? ????????????."); //URL
			return "redirect:/user/new";
		}
		
		boolean result = userService.InsertUser(userVO);
		
		if(result != true) {
			log.info("userRegist_fail = {}", userService.InsertUser(userVO));
			redirectAttributes.addFlashAttribute("userRegistMsg", environment.getProperty("user.regist.fail.msg"));
			return "redirect:/user/new";
		}
		
		redirectAttributes.addFlashAttribute("message", environment.getProperty("user.regist.success.msg"));
		return "redirect:/";
	}
	
	@ResponseBody
	@GetMapping(value = {"/user/DupCheck/{userId}", "/user/DupCheck/"}) //{userId} ??? null?????? 404?????? -- 2?????? ????????? ??????
	public Map<String, String> userIdDupCheck(@PathVariable(required = false) String userId) {
		Map<String, String> result = userService.userIdDupCheck(userId);
		return result;
	}
	
	/**
	 * ????????? ???????????? ????????? ????????????
	 * @param gameListDto
	 * @param pageNum
	 * @param model
	 * @return String
	 * @throws Exception
	 */
	@GetMapping("/admin/gameList")
	public String adminGamePage(GameListDto gameListDto, @RequestParam(defaultValue = "1") int pageNum, Model model) throws Exception {
		PageInfo<GameListDto> games = new PageInfo<GameListDto>(userService.selectGameList_admin(pageNum, gameListDto));
		model.addAttribute("games", games);
		model.addAttribute("search", gameListDto);
		return "admin/gameListAdmin";
	}
	
	/**
	 * ????????? ???????????? ????????? ????????????
	 * @return String 
	 */
	@GetMapping("/admin/userList")
	public String adminUserpage(UserUpdateForm userUpdateForm) {
		return "admin/adminUserPage";
	}
}
