package weather.toyproject.haru.border.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Getter @Setter
public class BoardListVO {
	
	private Long boardListId; // 게시판관리 PK
	private String boardName; // 게시판 이름 

}
