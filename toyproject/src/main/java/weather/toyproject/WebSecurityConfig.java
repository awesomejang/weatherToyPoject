package weather.toyproject;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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
			.antMatchers("/", "/login", "/signup", "/user", "/h2-console/**").permitAll() // 누구나 접근가능 
			//.antMatchers("/").hasAnyRole("USER") // ADMIN의 auth는 ROLE_ADMIN, ROLE_USER의 형태 
			.antMatchers("/myService").hasAnyRole("USER") // ADMIN의 auth는 ROLE_ADMIN, ROLE_USER의 형태, hasAnyRole("auth", "auth"....) 이 중 하나만 있어도 접근가능 
			.antMatchers("/admin").hasRole("ADMIN") // hasRole함수는 자동으로 주어진 인자에 'ROLE_ 접두어를 붙이고 검사를 시작한다. 
			//.anyRequest().authenticated(); // 위의 설정 외 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 
		.and()
		  .csrf().disable() // CSRF토큰 인증 해제  
		//.csrf().ignoringAntMatchers("/h2-console/**").disable()
		 	 .formLogin() // 로그인 관련 설정 시작
			 	 .loginPage("/login") // 로그인 페이지 URL
			 	 //.usernameParameter("userId")
			 	 //.passwordParameter("password")
			 	 .loginProcessingUrl("/auth/loginProc")
			 	 .defaultSuccessUrl("/") // 로그인 성공시 default redirect url
			 	 //.failureUrl(null)
		.and()
			.logout() // 로그아웃 관련 설정 시
			 .logoutSuccessUrl("/login") // 로그아웃 성공시 default redirect url
			 .invalidateHttpSession(true); // 기존 세션날리기
	    
	}
	
	@Override
	//== 로그인 로직 처리==//
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(null)
	}
}
