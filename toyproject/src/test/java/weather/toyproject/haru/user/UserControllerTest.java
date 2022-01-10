package weather.toyproject.haru.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Contains;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import weather.toyproject.haru.user.service.UserService;

// 테스트 실행방법을 확장할때 사용하는 어노테이션 파라미터로 준 클래스에 따라 달라진다.
//컨트롤러를 테스트하고 싶은 경우 사용 서버를 기동하지 않고도 요청과 응답을- 보내고 받고? 처리할수있도록 한다.?
//@WebMvcTest(UserControllerTest.class) // 테스트 대상 컨트롤러 명시
@RunWith(SpringRunner.class) // SpringRunner.class(@SpringBootTest를 위함), SpringJUnit4ClassRunner
@PropertySource(value = "classpath:/com/message.properties", encoding = "UTF-8")
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) //default -> 임의의 포트 사용이유 -> 테스트 시 충돌방지를 위함 //SpringBootTest.WebEnvironment.MOCK, webEnvironment = WebEnvironment.RANDOM_PORT
@AutoConfigureMockMvc //MockMvc제공 -> @webMvcTest와 충돌 
public class UserControllerTest {

	// ==테스트는 다른곳에서 가져다 쓸게 아니기 때문에 다 주입해서 사용하는게 편리 ==//
	// TestRestTemplate로 요청해도 무관 -> 비용이 크다.
	@Autowired // 주입을 @WebMvcTest에서
	MockMvc mvc;
	
	//@MockBean -> springContainer가 테스트시 사용되고 Bean이 Container에 존재하면 사용
	//@Mock -> 단순한 Mock을 위한다면 @Mock
	
	@MockBean(name = "userSerivce") // name지정안하면 spring에서 어느것인지 판단이 안나서 오류발생
	private UserService userSerivce;
	/**
	@Mock //@MockBean으로 인식안됨 
	private Environment environment;
	*/
	
	@Test
	public void userNew() throws Exception {
		mvc.perform(get("/user/new"))
		   .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Transactional // rollback
	public void userRegistProcess() throws Exception{
		//given
		// 중복되는 키값을 위하 MultiValueMap사용
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
		multiValueMap.add("userId", "TEST");
		multiValueMap.add("password", "1q2w3e4r!");
		multiValueMap.add("secondPassword", "1q2w3e4r!");
		multiValueMap.add("userName", "TestName");
		multiValueMap.add("email", "test@test.com");
		
		//when
		ResultActions resultActions =  mvc.perform(post("/user/new")
				                          .params(multiValueMap))
										  .andDo(print());
		//then
		resultActions.andExpect(status().is3xxRedirection())
		           //.andExpect(model().attributeExists("userRegistMsg"));
			         .andExpect(flash().attributeExists("userRegistMsg"))
		             .andExpect(view().name("redirect:/"));
	}
	
	
	@Test
	@Transactional
	public void userRegistValid() throws Exception {
		//given 
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
		//multiValueMap.add("userId", "TEST");
		multiValueMap.add("password", "1q2w3e4r!");
		multiValueMap.add("secondPassword", "1q2w3e4r!");
		multiValueMap.add("userName", "TestName");
		multiValueMap.add("email", "test@test.com");
		
		//when
		ResultActions resultActions =  mvc.perform(post("/user/new")
										  .params(multiValueMap))
									      .andDo(print());
		
		//then
		resultActions.andExpect(status().is3xxRedirection())
		             .andExpect(flash().attributeExists("valid_userId"));
	}
	
	
}
