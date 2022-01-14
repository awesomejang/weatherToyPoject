package weather.toyproject.haru.user.service;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import weather.toyproject.haru.user.dao.UserRepository;
import weather.toyproject.haru.user.domain.UserVO;

//각 단계별 역활에 충실한 테스트코드가 필요하다. 
// Service단의 테스트는 DB쿼리결과가 아니다. 
// 그렇기에 쿼리로직은 Mock으로 생성하고 그에대한 로직을 테스트한다.
@RunWith(MockitoJUnitRunner.class) //SpringRunner.class(SpringContainer 로드됨)
public class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@InjectMocks // @Mock으로 생성된 객체가 @InjectMocks에 선언된 객체에 주입된다.(의존성 해결)
	UserService userService;
	
	
	@Test
	@DisplayName("입력된 사용자 등록처리")
	public void InsertUser() {
		//given
		UserVO userVO = new UserVO();
		when(userRepository.InsertUser(userVO)).thenReturn(1);
		
		//when
		Boolean result = userService.InsertUser(userVO);
		
		//then
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	@DisplayName("userId로 계정정보 조회")
	public void getUserById() {
		//given
		//when(userRepository.getUserById("TEST")).thenReturn(new UserVO().setUserId("TEST"));
		
	}
	
	

}
