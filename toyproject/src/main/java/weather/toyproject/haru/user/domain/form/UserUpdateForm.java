package weather.toyproject.haru.user.domain.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter @Getter
public class UserUpdateForm {
	
	private Long userNo; // USER_T(PK)
	private String userId; // USER_T(계정명)
	private String email; // USER_T(이메일)
	private String userName; // USER_T(닉네임)
	private String locked; // USER_T(계정 잠금여부)
	
	private String authName; // AUTH_T(권한명)
}
