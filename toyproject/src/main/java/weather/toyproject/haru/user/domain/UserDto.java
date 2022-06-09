package weather.toyproject.haru.user.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {
	
	private Long userNo; // USER_T(PK)
	private String userId; // USER_T(계정명)
	private String email; // USER_T(이메일)
	private String userName; // USER_T(닉네임)
	private String locked; // USER_T(계정 잠금여부)
	
	private String authName; // AUTH_T(권한명)
	
	//TODO 
	// 기본 생성자, 주요 데이터 생성자 VO받아서 정적 메소드 처리??? 
	
}
