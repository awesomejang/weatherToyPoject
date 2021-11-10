package weather.toyproject.haru.user.domain;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class AuthVO {

	private String authId;
	
	private String authName;
	
	private Timestamp regDate;
	
	private Timestamp modDate;
	
	private String del_yn;
	
}
