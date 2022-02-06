package weather.toyproject.haru.border.domain;

import java.security.Timestamp;

import lombok.Data;

@Data
public class BoardVO {
	private Long boardId;
	private Long boardListId;
	private String user_id;
	private String mod_user_id;
	private String username;
	private String title;
	private String content;
	private String delYn;
	private int hit;
	private Timestamp regdate;
	private Timestamp moddate;
}
