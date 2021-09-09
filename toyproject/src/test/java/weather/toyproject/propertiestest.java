package weather.toyproject;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import weather.toyproject.Weather.service.RequestService;

@SpringBootTest
public class propertiestest {
	
	@Autowired
	private RequestService requestService;

	@Test
	void propertiesData() {
		assertThat(requestService.getUrl()).isEqualTo("test");  
	}
}
