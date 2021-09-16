package weather.toyproject.Weather;

import java.net.SocketTimeoutException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import weather.toyproject.Weather.domain.ApiItem;
import weather.toyproject.httpRequest.RequestFactory;

@Component
public class WeatherApiRequest {
	
private final RequestFactory requestFactory;
	
	@Autowired
	public WeatherApiRequest(RequestFactory weatherRequestService) {
		this.requestFactory = weatherRequestService;
	}
	
	@SuppressWarnings("unchecked")
	public void WheatherRequest() {
		ResponseEntity<String> requestResult;
		List<ApiItem> apiItemList;
		
		try {
			requestResult = requestFactory.ApiRequestResult();
			if(requestResult.getStatusCodeValue() == 200) {
				apiItemList = requestFactory.JsonToList(requestResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
