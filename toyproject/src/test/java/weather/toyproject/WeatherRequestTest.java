package weather.toyproject;

import java.net.SocketTimeoutException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import weather.toyproject.Weather.service.WeatherRequestService;

@SpringBootTest
public class WeatherRequestTest {
	
	@Autowired
	RunMethod runMethod;
	@Autowired
	WeatherRequestService weatherRequestService;
	
	@Test
	void 기상청API() {
		try {
			weatherRequestService.ApiRequestResult();
		} catch (Exception e) {
			e.printStackTrace();
			e.getCause().getMessage();
		}
	}
	
}
