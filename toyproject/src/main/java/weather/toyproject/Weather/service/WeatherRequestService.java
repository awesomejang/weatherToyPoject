package weather.toyproject.Weather.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
	public String ApiRequestResult() throws Exception {
		String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
		String serviceKey = null;
		
		try {
			 serviceKey = URLDecoder.decode("waSLmIW1sOFIRsbM4h70q0TF9c%2FtnyWEeeR3UI4W17a2H1HUt3Axc5o02pFUSiugbSXREvEa68kVjIPLTbjpRw%3D%3D", "UTF-8");
			 //waSLmIW1sOFIRsbM4h70q0TF9c/tnyWEeeR3UI4W17a2H1HUt3Axc5o02pFUSiugbSXREvEa68kVjIPLTbjpRw==
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		 //restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class).getBody();
		
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public UriComponents uriComponentsBuilder() throws Exception {
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(this.url)
								.queryParam("serviceKey", this.ServiceKey)
								.queryParam("numOfRows", "10")
								.queryParam("base_date", "20210908")
								.queryParam("base_time", "0600")
								.queryParam("nx", "55")
								.queryParam("ny", "127")
								.queryParam("dataType", "JSON")
								.queryParam("pageNo", "1")
								.build(false); // 인코딩 false
		return builder;
	}
	

	
}
