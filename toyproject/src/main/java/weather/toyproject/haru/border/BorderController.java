package weather.toyproject.haru.border;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BorderController {
	
	@GetMapping(value = {"/game/{gameCode}", "/game"})
	public String gameBorderList(@PathVariable(required = false) Long gameCode) {
		log.debug("board selected gameCode ={}", gameCode);
		return "board/gameBoard";
	}
}
