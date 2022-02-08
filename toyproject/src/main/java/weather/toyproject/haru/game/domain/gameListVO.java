package weather.toyproject.haru.game.domain;

import java.security.Timestamp;

import lombok.Data;

@Data
public class gameListVO {

	private Long gameCode; // PK
	private String gameName; // 게임이름
	private String delYn; // 사용여부
	private Timestamp regDate; //등록일 
	private Timestamp modDate; //수정일
	
}
