package weather.toyproject.Weather;

import java.net.SocketTimeoutException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import weather.toyproject.Weather.domain.ApiItem;
import weather.toyproject.Weather.domain.ApiResponse_Total;
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
				ApiResponse_Total apiResponse_Total = (ApiResponse_Total)requestFactory.JsonToObject(requestResult);
				
				if(apiResponse_Total.getResponse().getHeader().getResultCode().equals("00")) {
					apiItemList = requestFactory.JsonToList(requestResult);
				} else {
					throw new IllegalStateException("데이터 요청이 실패하였습니다.");
				}
			} else {
				throw new IllegalStateException("서버의 상태가 원활하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
