package weather.toyproject;

import java.net.SocketTimeoutException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import weather.toyproject.Weather.WeatherApiRequest;
import weather.toyproject.Weather.service.WeatherRequestService;

@SpringBootTest
public class WeatherRequestTest {
	
	@Autowired
	WeatherApiRequest weatherApiRequest;
	
	@Test
	void test() {
		try {
			weatherApiRequest.WheatherRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
