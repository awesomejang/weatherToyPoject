package weather.toyproject.haru.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import weather.toyproject.haru.user.domain.AuthVO;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;

//@Mapper //Mybatis intterface선언
public interface UserMapper {

	UserVO getUserById(String userId);
	
	List<AuthVO> getMember_Auth(String userId);
	
	int InsertUser(UserVO userVO);
	
	int InsertUserAuthMapping(Long userNo);
}
