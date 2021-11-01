package weather.toyproject.haru.user.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
@Getter
public class CustomUserDetails implements UserDetails{
		
		private String id;
		
		private String password;
		
		private String auth;
		
		private String email;
		
		private boolean emailVerified;
		
		private boolean locked;
		
		private String nickname;
		
		
		
		private Collection<GrantedAuthority> authorities; // 권한 목록

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
			return password;
		}

		/**
		 * PK값 username : 사용자의 고유값 
		 */
		@Override
		public String getUsername() {
			return id;
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
			return locked;
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
