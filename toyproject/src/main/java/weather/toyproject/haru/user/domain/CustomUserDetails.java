package weather.toyproject.haru.user.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails extends UserVO implements UserDetails{
		
		UserVO userVO;
		
		private List<GrantedAuthority> authorities; // 권한 목록
		
		@Autowired
		public CustomUserDetails(UserVO userVO) {
			this.userVO = userVO;
		}

		//==권한 setter ==//
		public void setAuthorities(List<AuthVO> AuthList) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			
			for(AuthVO authVO : AuthList) {
				authorities.add(new SimpleGrantedAuthority(authVO.getAuthName()));
			}
			
			this.authorities = authorities;
		}

		/**
		 * 해당 사용자의 권한 목록
		 */
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

		/**
		 * 비밀번호
		 */
		@Override
		public String getPassword() {
			return userVO.getPassword();
		}

		/**
		 * PK값 username : 사용자의 고유값 
		 */
		@Override
		public String getUsername() {
			return userVO.getUserId();
		}

		/**
		 * 계정 만료 여부
		 * true : 만료 안됨
		 * false : 만료
		 * 계정 만료를 확인할 필요가 없다면 true 고정 리턴 
		 */
		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		/**
		 * true : 잠기지 않음
		 * false : 잠김
		 */
		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		/**
		 * 비밀번호 만료 여부
		 * true : 만료 안됨
		 * false : 만료
		 */
		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		/**
		 * 사용자 활성화 여부
		 * true : 활성화
		 * false : 비활성화
		 */
		@Override
		public boolean isEnabled() {
			return true;
		}


}
