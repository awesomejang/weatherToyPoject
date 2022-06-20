package weather.toyproject.haru.user.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Component
@ToString
public class UserVO {

	private Long userNo; 
	
	@NotBlank
	private String userId;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String secondPassword;
	
	@NotBlank
	private String userName;
	
	@NotBlank
	private String email;
	
	private String verified;
	
	private String locked;
	
	//private AuthVO authVO;
	
	private List<AuthVO> AuthList;
	
	// Date컬럼을 위한 속성
	private Timestamp regdate;
	
	private Timestamp moddate;
	
}
