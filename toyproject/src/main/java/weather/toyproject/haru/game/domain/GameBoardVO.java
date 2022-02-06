package weather.toyproject.haru.game.domain;

import lombok.Data;

@Data
public class GameBoardVO {

	private Long gameBoardId; // PK
	private Long gameCode; // FK
}
