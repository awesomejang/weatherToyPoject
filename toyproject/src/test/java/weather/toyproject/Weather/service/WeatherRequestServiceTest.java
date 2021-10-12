package weather.toyproject.Weather.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class WeatherRequestServiceTest {
	
	ApplicationContext ac = new AnnotationConfigApplicationContext();
	WeatherRequestService weatherRequestService;
	
	
	@BeforeEach
	public void beforeEach() {
		weatherRequestService = new WeatherRequestService(new RestTemplate());
	}
	
	
	@Test
	@DisplayName("비, 눈소식이 없을때 내용없는 String을 리턴한다.")
	void createTimeBufferTest() {
		String time = "DAM";
		int raincount = 0 , snowcount = 0;
		float totalrain = 0, totalsnow = 0;
		StringBuffer sb =  weatherRequestService.createTimeBuffer(time, raincount, totalrain, snowcount, totalsnow);
		assertTrue(sb.toString().trim().equals(""));
	}

}
