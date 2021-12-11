package weather.toyproject.haru.user.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weather.toyproject.haru.user.UserMapper;
import weather.toyproject.haru.user.domain.AuthVO;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;

@Repository
public class UserRepository {

	@Autowired
	UserMapper userMapper;
	
	public UserVO getUserById(String userId) {
		return userMapper.getUserById(userId);
	}
	
	public List<AuthVO> getMember_Auth(String userId) {
		return userMapper.getMember_Auth(userId);
	}
	
	public int InsertUser(UserVO userVO) {
		userMapper.InsertUser(userVO);
		return userMapper.InsertUserAuthMapping(userVO.getUserNo());
	}
	
	public int InsertAuth_Mapping(Long userNo) {
		return userMapper.InsertUserAuthMapping(userNo);
	}
	
}
