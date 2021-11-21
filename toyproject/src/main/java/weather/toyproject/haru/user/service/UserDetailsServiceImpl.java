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
	
	@Override
	public UserDetails loadUserByUsername(String userId) {
		System.out.println("=======================loadUserByUsername=============================");
		UserVO userVO = userRepository.getUserById(userId);
		if (userVO == null) {
			System.out.println("계정 정보가 없습니다.");
			throw new UsernameNotFoundException(userId);
		}
		//CustomUserDetails userDetails = new CustomUserDetails(userVO);
		//userDetails.setAuthorities(userVO.getAuthList());
		//System.out.println("userDetails : " + userDetails.getUserVO().getUserId());
		//log.debug("userDetails : " + userDetails.getUserVO().getUserId());
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(AuthVO authVO : userVO.getAuthList()) {
			authorities.add(new SimpleGrantedAuthority(authVO.getAuthName()));
		}
		return new User(userVO.getUserId(), userVO.getPassword(), authorities);
	}
	
}
