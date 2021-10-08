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
import java.util.Iterator;
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
import weather.toyproject.com.ComUtil;
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
								.queryParam("base_date", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)) // format(yyyymmdd) //LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
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
				sb.append("오늘의 최저기온은 " + item.getFcstValue() + "도 "); 
			}
			
			if(item.getCategory().equals("TMX")) {
				sb.append("최고기온은 " + item.getFcstValue() + "도 로 예상되며");
				sb.append(System.getProperty("line.separator"));
			}
			
			//==시간대별 날씨 처리를 위한 로직==//
			// 눈, 비 소식이 하나라도 있을 경우 입력으로 하면 불필요한 반복문 방지 가능 --> 추후 변경예정 
			if( Integer.parseInt(item.getFcstTime()) >= 300 && Integer.parseInt(item.getFcstTime()) <= 700) {
				DamList.add(item);
				}
			if( Integer.parseInt(item.getFcstTime()) >= 800 && Integer.parseInt(item.getFcstTime()) <= 1500) {
				AmList.add(item);
				}
			if( Integer.parseInt(item.getFcstTime()) >= 1600 && Integer.parseInt(item.getFcstTime()) <= 2300) {
				PmList.add(item);
				}
			}
		//==시간대별 처리==//
		timeSplitMap.put("DAM", DamList);
		timeSplitMap.put("AM", AmList);
		timeSplitMap.put("PM", PmList);
		sb.append(this.JsonTimeAnaly(timeSplitMap));
		return sb.toString();
	}
	
	public StringBuffer JsonTimeAnaly(Map<String, List<ApiItem>> timeSplitMap) {
		
		StringBuffer sb = new StringBuffer();
		sb.append(System.getProperty("line.separator"));
		
		for(String keys : timeSplitMap.keySet()) {
			int raincount = 0, snowcount = 0;
			float totalrain = 0, totalsnow = 0; // 정확한 소수점 연산을 위해서는 BigDecimal사용해야함
			
			for(ApiItem item : timeSplitMap.get(keys)) {
					if(item.getCategory().equals("PCP") && item.getFcstValue().contains("mm")) {
						int rainIndex = item.getFcstValue().indexOf("mm");
							raincount++;
							totalrain += Float.parseFloat(item.getFcstValue().substring(0, rainIndex));
					}
				if(item.getCategory().equals("PTY") && item.getFcstValue().contains("mm")) {
					int snowIndex = item.getFcstValue().indexOf("mm");
						snowcount++;
						totalsnow += Float.parseFloat(item.getFcstValue().substring(0, snowIndex));
				}
			}
			
			if(!sb.toString().trim().equals("")) {
				sb.append(System.getProperty("line.separator"));
			}
			sb.append(createTimeBuffer(keys, raincount, totalrain, snowcount, totalsnow)); 
		}
		
		//이런 상황 만들지말자
		if(sb.toString().trim().equals("")) {
			sb.append("비 혹은 눈소식은 없습니다.");
		}
		
		return sb;
	}
	
	public StringBuffer createTimeBuffer(String time, int raincount, float totalrain, int snowcount, float totalsnow) {
		StringBuffer sb = new StringBuffer();
		
		if(raincount + snowcount > 0) {
			if(time.equals("DAM")) {
				sb.append("새벽에 ");
			} else if(time.equals("AM")) {
				sb.append("오전에 ");
			} else {
				sb.append("오후에 ");
			}
			
			if(raincount > 0 && snowcount == 0) {
				sb.append("비 소식이 예상됩니다.");
				sb.append(System.getProperty("line.separator"));
				sb.append("예상 강수량은 " + totalrain + "mm" + "입니다.");
			} else if (raincount == 0 && snowcount > 0) {
				sb.append("눈 소식이 예상됩니다.");
				sb.append(System.getProperty("line.separator"));
				sb.append("예상 적설량은 " + totalsnow + "mm" + "입니다.");
			} else if (raincount > 0 && snowcount > 0) { 
				sb.append("비 혹은 눈 소식이 예상됩니다.");
				sb.append(System.getProperty("line.separator"));
				sb.append("예상 강수량은 " + totalrain + "mm" + "입니다.");
				sb.append("예상 적설량은 " + totalsnow + "mm" + "입니다.");
			}
		} 
		return sb;
	}
	
}
	
