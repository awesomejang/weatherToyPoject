package weather.toyproject.haru.border.domain;

import java.security.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Getter @Setter
public class BoardVO {
	private Long boardId; // PK
	private Long boardListId; // FK(board_list 게시판관리)
	private String userId; // 등록자
	private String modUserId; // 수정자
	private String username; // 닉네임 
	private String title; // 글 제목
	private String content; // 글 내용
	private String delYn; // 삭제여부
	private int hit; // 조회수
	private Timestamp regdate; // 등록일자
	private Timestamp moddate; // 수정일자
	private int page;
	private int perPageNum;
	private int rowStart;
	private int rowEnd;
}
