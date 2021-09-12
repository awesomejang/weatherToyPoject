package weather.toyproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherRequestTest {
	
	@Autowired
	RunMethod runMethod;
	
	@Test
	void 기상청API() {
		Assertions.assertEquals(runMethod.WeatherInfoRequest(), "200");
	}
}
