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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import weather.toyproject.Weather.domain.ApiBody;
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
	 * 날씨 데이터 요청을 보낸 후 ReponseEntity를 리턴한다.  
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
	 * 
	 * @param requestResult
	 * @return List<ApiItem>
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@Override
	public List JsonToList(ResponseEntity<String> requestResult) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		JsonNode responseNode = objectMapper.readTree(requestResult.getBody()).findPath("item");
		CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, ApiItem.class);
		List<ApiItem> ApiItemList = objectMapper.readValue(responseNode.toString(), collectionType);
		return ApiItemList;
	}

	@Override
	public Object JsonToObject(ResponseEntity<String> responseEntity) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ApiResponse_Total apiResponse_Total = mapper.readValue(responseEntity.getBody(), ApiResponse_Total.class);
		return apiResponse_Total;
	}

	
}
