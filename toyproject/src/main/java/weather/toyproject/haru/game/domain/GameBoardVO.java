package weather.toyproject.haru.game.domain;

import lombok.Data;

@Data
public class GameBoardVO { //game_board 테이블 

	private Long gameBoardId; // PK
	private Long gameCode; // FK(game_list 테이블)
}
