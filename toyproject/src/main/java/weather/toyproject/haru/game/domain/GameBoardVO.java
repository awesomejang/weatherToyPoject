package weather.toyproject.haru.game.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import weather.toyproject.haru.border.domain.BoardVO;

//@Data
@Getter @Setter
public class GameBoardVO extends BoardVO { //game_board 테이블 

	private Long gameBoardId; // PK
	private Long gameCode; // FK(game_list 테이블)
}
