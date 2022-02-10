package weather.toyproject.haru.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import weather.toyproject.haru.user.dao.UserRepository;
import weather.toyproject.haru.user.domain.AuthVO;
import weather.toyproject.haru.user.domain.CustomUserDetails;
import weather.toyproject.haru.user.domain.UserVO;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override //로그인 처리
	public UserDetails loadUserByUsername(String userId) {
		
		UserVO userVO = userRepository.getUserById(userId);
		CustomUserDetails customUserDetails = new CustomUserDetails(userVO);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		if (userVO == null) {
			log.info("계정 정보가 없습니다. = {}", userId);
			throw new UsernameNotFoundException(userId); // 시큐리티 내부로직에서 해당 exception catch수행 시 BadCredentialsException을 throw
		}
		/**
		//==한 개 이상의 권한 입력==//
		for(AuthVO authVO : userVO.getAuthList()) {
			authorities.add(new SimpleGrantedAuthority(authVO.getAuthName()));
		}
		*/
		return customUserDetails;
	}
	
}
