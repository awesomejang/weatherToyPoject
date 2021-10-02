package weather.toyproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

//import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:com/ApiRequestInfo.properties")
public class propertiestest {
	
	@Value("${key.value}")
	private String key;
	
	@Autowired
	private Environment environment;
	
	//@Value("#{ApiResource['test.key']}")
	//private String definekey;
	
	@Value("${test.key}")
	private String definekey;
	
	
	@Test
	@DisplayName("application.propertis파일에서 데이터 가져오기")
	void valueCheck() {
		Assertions.assertEquals("testkey", environment.getProperty("key.value"));
	}
	
	@Test
	@DisplayName("사용자 정의 properties에서 데이터 가져오기")
	void defineValueCheck()  {
		Assertions.assertEquals("testkey", environment.getProperty("test.key"));
	}
	
	
	
}
