package weather.toyproject.haru.game.domain;

//import java.security.Timestamp;
import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import weather.toyproject.com.file.FileVO;

//@Data
@Getter @Setter
public class GameListVO {

	private Long gameCode; // PK
	@NotBlank(message = "게임이름을 입력해주세요.")
	private String gameName; // 게임이름
	private String userId; //등록자 계정명
	private String modUserId; // 수정자 계정명
	//@non
	private String delYn; // 사용여부
	private Timestamp regDate; //등록일 
	private Timestamp modDate; //수정일
	private Long fileId; // 파일FK
	private String fileAttYn; // 파일 업로드 여부
	
	/**
	 * 파일PK와 파일업로드여부를 변경한다.
	 * @param fileId
	 */
	public void setGameImageInfo(FileVO fileInfo) { 
		this.fileId = fileInfo.getFileId();
		this.fileAttYn = "Y";
	}
	
}
