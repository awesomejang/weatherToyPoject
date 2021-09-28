package weather.toyproject.Weather.service;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
		System.out.println(requestURI.toUriString());
	    requestResult = this.restTemplate.exchange(requestURI.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
	    return requestResult;
	}
	
	@Override
	public UriComponents uriComponentsBuilder()  {
		String serviceKey = null;
		try {
			serviceKey = URLDecoder.decode(this.ServiceKey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(this.url)
								.queryParam("serviceKey", serviceKey)
								.queryParam("numOfRows", "233") // 776(오늘포함3일), 233(요청일하루)
								.queryParam("base_date", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)) // format(yyyymmdd) //LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE
								.queryParam("base_time", "0200")
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
		/*
		for(ApiItem item : ApiItemList) {
			System.out.println(item);
		}
		*/
		return ApiItemList;
	}

	@Override
	public Object JsonToObject(ResponseEntity<String> responseEntity) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ApiResponse_Total apiResponse_Total = mapper.readValue(responseEntity.getBody(), ApiResponse_Total.class);
		return apiResponse_Total;
	}

	
	public String JsonDataAnaly(List<ApiItem> ApiItemList) {
		StringBuffer sb =  new StringBuffer();
		Map<String, List<ApiItem>> timeSplitMap = new HashMap<String, List<ApiItem>>();
		List<ApiItem> DamList = new ArrayList<ApiItem>();
		List<ApiItem> AmList = new ArrayList<ApiItem>();
		List<ApiItem> PmList = new ArrayList<ApiItem>();
		
		
		for(ApiItem item : ApiItemList) {
			//==최저, 최고 기온==//
			if(item.getCategory().equals("TMN")) {
				sb.append("오늘의 최저기온은 " + item.getFcstValue() + "도 " + "최고기온은 " + item.getFcstValue() + "도 로 예상되며" ); 
				sb.append(System.getProperty("line.separator"));
			}
			
			//==시간대별 날씨 처리를 위한 로직==//
			// 눈, 비 소식이 하나라도 있을 경우 입력으로 하면 불필요한 반복문 방지 가능 --> 추후 변경예정 
			if( Integer.parseInt(item.getFcstTime()) >= 300 && Integer.parseInt(item.getFcstTime()) <= 700) {
				DamList.add(item);
				}
			if( Integer.parseInt(item.getFcstTime()) >= 800 && Integer.parseInt(item.getFcstTime()) <= 700) {
				AmList.add(item);
				}
			if( Integer.parseInt(item.getFcstTime()) >= 1600 && Integer.parseInt(item.getFcstTime()) <= 2300) {
				PmList.add(item);
				}
			}
		
		//==시간대별 처리==//
		timeSplitMap.put("Dam", DamList);
		timeSplitMap.put("Am", AmList);
		timeSplitMap.put("Pm", PmList);
		
		JsonTimeAnaly(timeSplitMap);
		
		for (int i = 0; i < DamList.size(); i++) {
			System.out.println(DamList.get(i));
		}
		return sb.toString();
		}
	
	public StringBuffer JsonTimeAnaly(Map<String, List<ApiItem>> timeSplitMap) {
		
		StringBuffer DamSb = new StringBuffer();
		int raincount = 0, snowcount = 0;
		float totalrain = 0, totalsnow = 0; // 정확한 소수점 연산을 위해서는 BigDecimal사용해야함
		
		for(ApiItem item : timeSplitMap.get("Dam")) {
			if(item.getCategory().equals("PTY") && item.getFcstValue().equals("1")) {
				if(item.getCategory().equals("PCP")) {
					raincount++;
					totalrain += Float.parseFloat(item.getFcstValue());
				}
			}
			if(item.getCategory().equals("PTY") && item.getFcstValue().equals("3")) {
				if(item.getCategory().equals("SNO")) {
					snowcount++;
					totalsnow += Float.parseFloat(item.getFcstValue());
				}
			}
		}
		
		//==비, 눈 텍스트생성==//
		if(raincount > 0 && snowcount == 0) {
			DamSb.append("새벽에 비 소식이 예상됩니다.");
			DamSb.append(System.getProperty("line.separator"));
			DamSb.append("시간당 평균 예상 강수량은 " + totalrain + "mm" + "입니다.");
		} else if (raincount == 0 && snowcount > 0) {
			DamSb.append("새벽에 비 혹은 눈 소식이 예상됩니다.");
			DamSb.append(System.getProperty("line.separator"));
			DamSb.append("예상 강수량은 " + totalrain + "mm" + "입니다.");
		}
		System.out.println("raincount = " + raincount + " , " + "totalrain = " + totalrain);
		System.out.println("raincount = " + snowcount + " , " + "totalrain = " + totalsnow);
		return DamSb;
	}
	
	public StringBuffer CreateTimeBuffer(String time, int raincount, int totalrain, int snowcount, int totalsnow) {
		return new StringBuffer();
	}
	
	
}
	
