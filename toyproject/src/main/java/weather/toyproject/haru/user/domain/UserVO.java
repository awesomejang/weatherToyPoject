package weather.toyproject.haru.user.domain;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

//@Component
@Getter
@Setter
public class UserVO {

	private Long userNo; 
	
	private String UserId;
	
	private String Password;
	
	private String UserName;
	
	private String email;
	
	private String verified;
	
	private String locked;
	
	private List<AuthVO> AuthList;
	
	private Timestamp Regdate;
	
}
