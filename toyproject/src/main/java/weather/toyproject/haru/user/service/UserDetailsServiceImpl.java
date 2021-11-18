package weather.toyproject.haru.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import weather.toyproject.haru.user.dao.UserRepository;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		UserVO userVO = userRepository.getUserById(id);
		if (userVO == null) {
			throw new UsernameNotFoundException("계정이 없어요");
		}
		return new CustomUserDetails(userVO);
		
	}

}
