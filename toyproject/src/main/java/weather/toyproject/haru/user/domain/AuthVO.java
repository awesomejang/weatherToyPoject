package weather.toyproject.haru.user.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthVO {

	private String authId;
	
	private String authName;
	
	private Timestamp regDate;
	
	private Timestamp modDate;
	
	private String del_yn;
	
}
