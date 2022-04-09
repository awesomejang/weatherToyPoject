package weather.toyproject.haru.game;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import weather.toyproject.com.AuthUtil;
import weather.toyproject.com.file.FileUtil;
import weather.toyproject.com.file.FileVO;
import weather.toyproject.haru.game.domain.GameListVO;
import weather.toyproject.haru.game.service.GameService;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameController {
	
	private final GameService gameService;

	/**
	 * 관리자 게임등록페이지 Controller
	 * @return String
	 */
	@GetMapping("/admin/game/regist")
	public String gameRegistPage(HttpServletRequest reqeust, Model model, Map<String, String> validResult) {
		model.addAttribute("validResult", validResult.get("validMap")); 
		return "game/gameRegist";
	}
	
	/**
	 * 관리자 게임등록처리 Controller
	 * @return String
	 */
	//@ResponseBody
	@PostMapping("/admin/game/regist")
	public String gameRegist(HttpServletRequest reqeust, HttpServletResponse reponse , @ModelAttribute FileVO files, 
			@Valid GameListVO gameListVO, BindingResult bindingResult
			, Errors errors , Model model, RedirectAttributes redirectAttribute) throws IOException {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> validResult = this.GameValidHandle(errors);
			redirectAttribute.addFlashAttribute("validMap", validResult);
			return "redirect:/admin/game/regist";
		} 
		
		Boolean result = gameService.gameUpload(files.getMultipartFile(), gameListVO);
		
		if(result) {
			redirectAttribute.addAttribute("msg", "게임업로드에 성공했습니다.");
			return "redirect:/admin/gameList";
		}
		redirectAttribute.addAttribute("msg", "게임업로드에 실패했습니다.");
		return "redirect:/admin/game/gameRegist";
	}
	
	private Map<String, String> GameValidHandle(Errors errors) {
		Map<String, String> validatorResult = new HashMap<String, String>();
		for(FieldError error : errors.getFieldErrors()) { // 캡슐화된 Error객체
			String validkeyName = String.format("valid_%s", error.getField()); //공통적인 메시지 처리에 유리
			log.info("validation check = {}", validkeyName);
			validatorResult.put(validkeyName, error.getDefaultMessage()); //VO에 선언한 메세지
		}
		return validatorResult;
	}
}
