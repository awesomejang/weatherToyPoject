package weather.toyproject.Weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class proTest {

	@Autowired
	Environment environment;
	
	
	public void test() {
		System.out.println("test.key="  + environment.getProperty("test.key"));
	}
}
