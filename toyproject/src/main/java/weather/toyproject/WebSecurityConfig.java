package weather.toyproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//== 인증, 권한이 스프링시큐리티의 핵심==//
@EnableWebSecurity //SpringSecurityFilterChain 추가 레거시에서 web.xml에 DelegatingFilterProxy라는 springSecurityFilterChain을 등록하는걸로 시작하는것과 같은 이치 
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	//== 인증을 무시할 경로들을 설정 ==// 
	public void configure(WebSecurity web) {
		//static 하위폴더 설정
		web.ignoring()
		   .antMatchers("/css/**", "/script/**", "/img/**")
		   .antMatchers("/resources/**");
	}
	
	@Override
	//== HttpSecurity 인스턴스를 통한 자신만의 인증 로직 설정 ==//
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/index.html", "/login", "/signup", "/user").permitAll() // 누구나 접근가능 
			.antMatchers("/").hasAnyRole("USER") // ADMIN의 auth는 ROLE_ADMIN, ROLE_USER의 형태 
			.antMatchers("/admin").hasRole("ADMIN")
			//.anyRequest().authenticated(); // 위의 설정 외 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 
		.and()
		 	.formLogin() // 로그인 관련 설정 시작
		 	 .loginPage("/login") // 로그인 페이지 URL
		 	 .defaultSuccessUrl("/") // 로그인 성공시 default redirect url 
		.and()
			.logout() // 로그아웃 관련 설정 시
			 .logoutSuccessUrl("/login") // 로그아웃 성공시 default redirect url
			 .invalidateHttpSession(true) // 기존 세션날리기
	    ;
	}
	
	@Override
	//== 로그인 로직 처리==//
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(null)
	}
}
