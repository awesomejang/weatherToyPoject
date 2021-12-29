package weather.toyproject.haru.user;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

// 테스트 실행방법을 확장할때 사용하는 어노테이션 파라미터로 준 클래스에 따라 달라진다.
@RunWith(SpringRunner.class) //SpringRunner.class(@SpringBootTest를 위함), SpringJUnit4ClassRunner
//컨트롤러를 테스트하고 싶은 경우 사용 서버를 기동하지 않고도 요청과 응답을- 보내고 받고? 처리할수있도록 한다.?
@WebMvcTest(UserController.class) //테스트 대상 컨트롤러 명시 
public class UserControllerTest {
	
	//==테스트는 다른곳에서 가져다 쓸게 아니기 때문에 다 주입해서 사용하는게 편리 ==//
	
	@Autowired //주입을 @WebMvcTest에서 
	MockMvc mvc;
	
	
	@Test
	void userNew() {
		//mvc.perform()
	}
}
