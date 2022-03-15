package weather.toyproject.com;

import org.springframework.security.core.context.SecurityContextHolder;

import weather.toyproject.haru.user.domain.CustomUserDetails;

public class AuthUtil {
	
	/**
	 * 세션에 등록된 LoginVO를 리턴한다.
	 * @return Object
	 */
	public static Object getLoginSession() {
		CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getUserVO();
	}

}
