package weather.toyproject.haru.border;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BorderController {
	
	@GetMapping(value = {"/game/{gameCode}", "/game"})
	public String gameBorderList(@PathVariable(required = false) Long gameCode) {
		log.debug("board selected gameCode ={}", gameCode);
		return "board/gameBoard";
		//return "game/{gameCode}";
	}
	
}
