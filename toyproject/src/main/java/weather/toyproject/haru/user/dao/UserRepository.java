package weather.toyproject.haru.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weather.toyproject.haru.user.UserMapper;
import weather.toyproject.haru.user.domain.CustomUserDetails;

@Repository
public class UserRepository {

	//@Autowired
	UserMapper userMapper;
	
	public CustomUserDetails getUserById(String id) {
		return userMapper.getUserById(id);
	}
	
}
