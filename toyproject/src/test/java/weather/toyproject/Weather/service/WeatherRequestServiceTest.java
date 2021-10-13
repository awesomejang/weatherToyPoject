package weather.toyproject.Weather.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.SocketTimeoutException;


@SpringBootTest
public class WeatherRequestServiceTest {
	
	
	@Autowired
	WeatherRequestService weatherRequestService;
	
	/**
	@BeforeEach
	public void beforeEach() {
		weatherRequestService = new WeatherRequestService(new RestTemplate());
	}
	*/
	@Test
	void uriComponentsBuilderTest() {
		UriComponents build =  weatherRequestService.uriComponentsBuilder();
		System.out.println(build.toString());
	}
	
	
	@Test
	@DisplayName("공공데이터 서버로 요청한 결과가 200 SUCCESS이다.")
	void ApiRequestResultTest() {
		try {
			ResponseEntity responseEntity = weatherRequestService.ApiRequestResult();
			Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
			Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
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
