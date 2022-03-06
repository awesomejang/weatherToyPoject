package weather.toyproject.haru.game;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GameController {

	@GetMapping("/admin/game/regist")
	public String gameRegist() { 
		return "game/gameRegist";
	}
}
