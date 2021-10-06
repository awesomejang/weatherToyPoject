package weather.toyproject.Weather.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherRequestServiceTest {
	
	@Autowired
	WeatherRequestService weatherRequestService;
	
	
	@Test
	@DisplayName("비, 눈소식이 없을때 내용없는 String을 리턴한다.")
	void createTimeBufferTest() {
		String time = "DAM";
		int raincount = 0 , snowcount = 0;
		float totalrain = 0, totalsnow = 0;
		StringBuffer sb =  weatherRequestService.createTimeBuffer(time, raincount, totalrain, snowcount, totalsnow);
		Assertions.assertEquals(sb.toString().trim().equals(""), true); 
	}

}
