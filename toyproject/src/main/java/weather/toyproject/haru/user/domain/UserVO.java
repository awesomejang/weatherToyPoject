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
	
	@NotBlank(message = "아이디를 입력해주세요.")
	private String userId;
	
	@NotBlank(message = "비밀번호를 입력해주세요.")
	private String password;
	
	private String secondPassword;
	
	@NotBlank(message = "닉네임을 입력해주세요.")
	private String userName;
	
	@NotBlank(message = "이메일을 입력해주세요.")
	private String email;
	
	private String verified;
	
	private String locked;
	
	private List<AuthVO> AuthList;
	
	// Date컬럼을 위한 속성
	private Timestamp regdate;
	
	private Timestamp moddate;
	
}
