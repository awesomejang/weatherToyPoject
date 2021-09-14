package weather.toyproject.Weather.service;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import weather.toyproject.Weather.domain.ApiItem;
import weather.toyproject.Weather.domain.ApiResponse_Total;
import weather.toyproject.httpRequest.RequestFactory;

//@PropertySource("classpath:/com/ApiRequestInfo.properties")
@Service
public class WeatherRequestService implements RequestFactory {
	
	@Value("#{ApiResource['weather.api.url']}")
	private String url;
	
	@Value("#{ApiResource['weather.api.key']}")
	private String ServiceKey;
	
	private final RestTemplate restTemplate;
	
	@Autowired
	public WeatherRequestService(RestTemplate restTemplate) {
		this.restTemplate  = restTemplate;
	}

	/**
	 * 날씨 데이터 요청을 보낸 후 결과를 파싱하여 
	 */
	@Override
	public ResponseEntity ApiRequestResult() throws  SocketTimeoutException {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> requestResult;
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		UriComponents requestURI = this.uriComponentsBuilder();
		
	    requestResult = this.restTemplate.exchange(requestURI.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
	    return requestResult;
	    
	}
	
	@Override
	public UriComponents uriComponentsBuilder()  {
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(this.url)
								.queryParam("serviceKey", this.ServiceKey)
								.queryParam("numOfRows", "10")
								.queryParam("base_date", "20210914")
								.queryParam("base_time", "0600")
								.queryParam("nx", "55")
								.queryParam("ny", "127")
								.queryParam("dataType", "JSON")
								.queryParam("pageNo", "1")
								.build(false); // 인코딩 false
		return builder;
	}
	/**
	public List JsonToList(ResponseEntity requestResult) {
		ObjectMapper objectMapper = new ObjectMapper();
		//List<ApiItem> ApiItemList =  objectMapper.readValue(responseNode.toString(), collectionType);
		
	}
	*/
}
