package weather.toyproject.security;

import java.io.IOException;
import javax.security.auth.login.CredentialException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String errorMessage;
		
		//loadUserByUsername수행 시 발생 		
		if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
		}
		else if(exception instanceof DisabledException) { // loadUserByUsername에서 throw해서 확인필요 
			errorMessage = "계정이 비활성화 되었습니다. 관리자에게 문의바랍니다."; 
		}
		else if(exception instanceof CredentialsExpiredException) {
			errorMessage = "비밀번호 유효기간이 만료되었습니다. 관리자에게 문의바랍니다.";
		}
		else {
			errorMessage = "알수 없는 이유로 로그인에 실패했습니다. 관리자에게 문의바랍니다.";
		}
		//redirectStratgy.sendRedirect(request, response, "/login?error=true");
		//response.sendRedirect("/login/fail");
		request.setAttribute("message", errorMessage);
		RequestDispatcher disPatcher =  request.getRequestDispatcher("/login?error=true"); // 서버 to 서버(forward)
		disPatcher.forward(request, response);
		
	}
}
