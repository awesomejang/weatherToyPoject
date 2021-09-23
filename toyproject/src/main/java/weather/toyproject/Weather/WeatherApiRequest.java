package weather.toyproject.Weather;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import weather.toyproject.Weather.domain.ApiItem;
import weather.toyproject.Weather.domain.ApiResponse_Total;
import weather.toyproject.Weather.service.WeatherRequestService;

@Component
public class WeatherApiRequest {
	
private final WeatherRequestService weatherRequestService;
	
	@Autowired
	public WeatherApiRequest(WeatherRequestService weatherRequestService) {
		this.weatherRequestService = weatherRequestService;
	}
	
	@SuppressWarnings("unchecked")
	public void WheatherRequest() {
		ResponseEntity<String> requestResult;
		List<ApiItem> apiItemList;
		
		try {
			requestResult = weatherRequestService.ApiRequestResult();
			
			if(requestResult.getStatusCodeValue() == 200) {
				ApiResponse_Total apiResponse_Total = (ApiResponse_Total)weatherRequestService.JsonToObject(requestResult);
				
				if(apiResponse_Total.getResponse().getHeader().getResultCode().equals("00")) {
					apiItemList = weatherRequestService.JsonToList(requestResult);
					weatherRequestService.JsonDataAnaly(apiItemList);
				} else {
					throw new IllegalStateException("날씨 데이터 요청결과가 정확하지 않습니다.");
				}
			} else {
				throw new IllegalStateException("서버의 상태가 원활하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
