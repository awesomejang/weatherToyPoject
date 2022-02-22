package weather.toyproject;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.AllArgsConstructor;
import weather.toyproject.haru.user.service.UserDetailsServiceImpl;
import weather.toyproject.security.CustomAuthFailureHandler;
import weather.toyproject.security.CustomAuthSuccessHandler;

//== 인증, 권한이 스프링시큐리티의 핵심==//
@EnableWebSecurity //SpringSecurityFilterChain 추가 레거시에서 web.xml에 DelegatingFilterProxy라는 springSecurityFilterChain을 등록하는걸로 시작하는것과 같은 이치 
@Configuration
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	//private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
			
	
	
	//== 인증을 무시할 경로들을 설정 ==// 
	@Override
	public void configure(WebSecurity web) { //==HttpSecurity : 보안처리, WebSecurity : 보안예외처리(정적리소스, HTML)
		//==myService 만들고 나서 다시 테스트 ==//
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // static한 resource의 대한 모든 접근 가능
		web.ignoring().antMatchers("/resources/**/**/**/**")
		              .antMatchers("/vendor/**/**/**/**")
		              .antMatchers("/h2-console/**");
		//static 하위폴더 설정
		//web.ignoring().antMatchers("/css/**", "/script/**/**", "/img/**", "/vender/**");
		   //.antMatchers("/resources/**");
	}
	
	@Override
	//== HttpSecurity 인스턴스를 통한 자신만의 인증 로직 설정 ==//
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests() // 해당 메소드 아래로 각 경로에 따른 권한을 지정할 수 있다. 
			.antMatchers("/", "/login/**", "/signup", "/user/**", "/h2-console/**").permitAll() // 누구나 접근가능 
			//.antMatchers("/").hasAnyRole("USER") // ADMIN의 auth는 ROLE_ADMIN, ROLE_USER의 형태 
			.antMatchers("/myService").hasAnyRole("USER") // ADMIN의 auth는 ROLE_ADMIN, ROLE_USER의 형태, hasAnyRole("auth", "auth"....) 이 중 하나만 있어도 접근가능 
			.antMatchers("/admin").hasRole("ADMIN") // hasRole함수는 자동으로 주어진 인자에 'ROLE_ 접두어를 붙이고 검사를 시작한다. 
			//.anyRequest().authenticated(); // 위의 설정 외 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 
		.and()
		  	.csrf().disable() // CSRF토큰 인증 해제  
		  //.csrf().ignoringAntMatchers("/h2-console/**").disable()
		 	 .formLogin() // 로그인 관련 설정 시작
			 	 .loginPage("/login") // 로그인 페이지 URL
			 	 .loginProcessingUrl("/loginProcess")
			 	 .usernameParameter("userId")
			 	 .passwordParameter("password")
			 	 //.defaultSuccessUrl("/login", true) // 로그인 성공시 default redirect url
			 	 // 하단에 선언한 FailHandler 등록 
			 	 .successHandler(successHandler())
			 	 .failureHandler(failureHandler())
			 	 //.failureUrl("/login?error=true")
			 .permitAll() // 파악 필요 
		.and()
			.logout() // 로그아웃 관련 설정 시
				.logoutUrl("/logout") // csrf활성화면 POST만 처리 끄면 GET도 처리  
	        	.logoutSuccessUrl("/") // 로그아웃 성공시 default redirect url
	        	.invalidateHttpSession(true) // 기존 세션날리기
	    .and()
	    	.exceptionHandling() // 에러 처리
	    	.accessDeniedPage("/noAuthPage.html"); // 에러 시 이동할 페이지
	}
	
	
	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new CustomAuthFailureHandler();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new CustomAuthSuccessHandler();
	}
	
	
	//@Override
	/**
	 * 로그인 로직 처리 
	 * 없으면 default로 UserDetailsService구현체 쓰는듯 
	 */
	//public void configure(AuthenticationManagerBuilder auth) throws Exception {
	//	auth.userDetailsService(userDetailsService);
	//	auth.eraseCredentials(false);
	//}
}
