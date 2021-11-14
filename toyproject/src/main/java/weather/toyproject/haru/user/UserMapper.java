package weather.toyproject.haru.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import weather.toyproject.haru.user.domain.AuthVO;
import weather.toyproject.haru.user.domain.CustomUserDetails;

//@Mapper
public interface UserMapper {

	CustomUserDetails getUserById(String id);
	

}
