package weather.toyproject.haru.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import weather.toyproject.haru.user.service.UserService;

// 테스트 실행방법을 확장할때 사용하는 어노테이션 파라미터로 준 클래스에 따라 달라진다.
//컨트롤러를 테스트하고 싶은 경우 사용 서버를 기동하지 않고도 요청과 응답을- 보내고 받고? 처리할수있도록 한다.?
//@WebMvcTest(UserControllerTest.class) // 테스트 대상 컨트롤러 명시
@RunWith(SpringRunner.class) // SpringRunner.class(@SpringBootTest를 위함), SpringJUnit4ClassRunner
@PropertySource(value = "classpath:/com/message.properties", encoding = "UTF-8")
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) //default -> 임의의 포트 사용이유 -> 테스트 시 충돌방지를 위함 //SpringBootTest.WebEnvironment.MOCK, webEnvironment = WebEnvironment.RANDOM_PORT
@AutoConfigureMockMvc //MockMvc제공 -> @webMvcTest와 충돌 
//@AutoconfigureMy
public class UserControllerTest {

	// ==테스트는 다른곳에서 가져다 쓸게 아니기 때문에 다 주입해서 사용하는게 편리 ==//

	@Autowired // 주입을 @WebMvcTest에서
	MockMvc mvc;
	
	@MockBean
	private UserService userSerivce;
	
	@Mock //@MockBean으로 인식안됨 
	private Environment environment;

	@Test
	public void userNew() throws Exception {
		mvc.perform(get("/user/new"))
		   .andExpect(MockMvcResultMatchers.status().isOk());
	}
}
