package weather.toyproject;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import weather.toyproject.Weather.WeatherApiRequest;
import weather.toyproject.Weather.service.WeatherRequestService;

@SpringBootTest
public class WeatherRequestTest  implements ApplicationRunner{
	
	@Autowired
	WeatherApiRequest weatherApiRequest;
	
	
	@Test
	void test() {
		try {
			weatherApiRequest.WeatherRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void LocalDateTest() {
		String todayDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		System.out.println(todayDate);
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		
		sb.append("sbsb");
		sb2.append("sb2sb2sb");
		
		sb.append(sb2);
		System.out.println(sb.toString());
	}
	
	@Test
	void run() {
		String rain = "1.29";
		Float.parseFloat(rain);
		System.out.println("float = " + Float.parseFloat(rain));
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
