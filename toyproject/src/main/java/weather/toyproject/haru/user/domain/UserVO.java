package weather.toyproject.haru.user.domain;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class UserVO {

	private Long userNo; 
	
	private String userId;
	
	private String password;
	
	private String userName;
	
	private String email;
	
	private String verified;
	
	private String locked;
	
	private List<AuthVO> AuthList;
	
	private Timestamp regdate;
	
	private Timestamp moddate;
	
}
