package weather.toyproject.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler{

	private RequestCache requestCache = new HttpSessionRequestCache(); // RequestCache : 로그인 화면을 보여주기 전에 사용자 요청을 저장하고 이를 관리하는 인터페이스
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); // RedirectStrategy : 시큐리티가 화면 이동에 대한 규칙을 정의하는 부분을 만든 인터페이스 -> 사용자가 임의의 로그인페이지 생성시 사용 불가 
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		ClearAuthenticationAttributes(request);
		// redirect page
		redirectStrategy(request, response, authentication);
		
	}
	
	private void ClearAuthenticationAttributes(HttpServletRequest request) {
		// 존재하는 세션이 있으면 return하고 없으면 새로 생성하지 않는다(false)
		HttpSession session = request.getSession(false);
		
		if(session == null) return;
		
		// 로그인 실패 시 세션에 실패에 대한 내용이 저장된 세션을 제거한다. 
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		
		
	}
	
	private void redirectStrategy(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		// 
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(savedRequest == null) {
			redirectStrategy.sendRedirect(request, response, "/");
		}
		else {
			log.info("redirectStrategy");
			String targetUrl = request.getHeader("REFERER");
			
			redirectStrategy.sendRedirect(request, response, targetUrl);
		}
	}

}
