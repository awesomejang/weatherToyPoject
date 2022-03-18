package weather.toyproject.haru.game;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import weather.toyproject.com.AuthUtil;
import weather.toyproject.com.file.FileUtil;
import weather.toyproject.com.file.FileVO;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;

@Slf4j
@Controller
public class GameController {
	
	@Autowired
	FileUtil fileUtil;

	/**
	 * 관리자 게임등록페이지 Controller
	 * @return String
	 */
	@GetMapping("/admin/game/regist")
	public String gameRegistPage(HttpServletRequest reqeust) {
		
		return "game/gameRegist";
	}
	
	/**
	 * 관리자 게임등록처리 Controller
	 * @return String
	 */
	@ResponseBody
	@PostMapping("/admin/game/regist")
	public String gameRegist(HttpServletRequest reqeust, @ModelAttribute FileVO files, @RequestParam String gameName, Principal principal) throws IOException {
		
	    //fileUtil.store(files.getMultipartFile());
		for(MultipartFile file : files.getMultipartFile()) {
			//log.info("file.orginalName = {}", file.getOriginalFilename());
		}
		//log.info("gameName = {}", gameName);
		//return "game/gameRegist";
		return "OK";
	}
}
