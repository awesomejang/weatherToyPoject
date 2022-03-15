package weather.toyproject.haru.user.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails extends UserVO implements UserDetails{

	private UserVO userVO;
	
	//protected 생성자면 같은 패키지에서만 사용가능
	public CustomUserDetails() {
		
	}
	
	public CustomUserDetails(UserVO userVO) {
		this.userVO = userVO;
	}
	
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
	public void setAuthorities() {
		for(AuthVO authVO : userVO.getAuthList()) {
			authorities.add(new SimpleGrantedAuthority(authVO.getAuthName()));
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		setAuthorities();
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return userVO.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userVO.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
		
		

}
