package weather.toyproject.haru.user.domain;

import java.sql.Timestamp;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class GameListDto {
	
	private Long gameCode; // PK
	private String gameName; // 게임이름
	private String userId; //등록자 계정명
	private String modUserId; // 수정자 계정명
	private String delYn; // 사용여부
	private Timestamp regDate; //등록일 
	private Timestamp modDate; //수정일
	private String fileId; // 파일FK
	private String fileAttYn; // 파일 업로드 여부

}
