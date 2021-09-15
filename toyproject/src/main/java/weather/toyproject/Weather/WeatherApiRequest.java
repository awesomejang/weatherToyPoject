package weather.toyproject.Weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import weather.toyproject.httpRequest.RequestFactory;

@Component
public class WeatherApiRequest {
	
	private final RequestFactory requestFactory;
	
	@Autowired
	public WeatherApiRequest(RequestFactory weatherRequestService) {
		this.requestFactory = weatherRequestService;
	}
	
	public void test() {
		System.out.println(this.requestFactory.getClass());
	}
}
