package weather.toyproject.haru.user;

import org.apache.ibatis.annotations.Mapper;
import weather.toyproject.haru.user.domain.CustomUserDetails;

@Mapper
public interface UserMapper {

	CustomUserDetails getUser(String id, String password);

}
