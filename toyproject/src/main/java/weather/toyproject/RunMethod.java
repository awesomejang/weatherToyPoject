package weather.toyproject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import weather.toyproject.Weather.domain.ApiBody;
import weather.toyproject.Weather.domain.ApiItem;
import weather.toyproject.Weather.domain.ApiResponse_Total;

@Component
public class RunMethod {
	
	/*
	 * @Autowired RestTemplate restTemplate;
	 */
	
	public void WeatherInfoRequest() {
		try {
			 ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
				
				RestTemplate restTemplate = applicationContext.getBean("restTemplate", RestTemplate.class);
				String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
				String serviceKey = null;
				try {
					 serviceKey = URLDecoder.decode("waSLmIW1sOFIRsbM4h70q0TF9c%2FtnyWEeeR3UI4W17a2H1HUt3Axc5o02pFUSiugbSXREvEa68kVjIPLTbjpRw%3D%3D", "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
				UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
										.queryParam("serviceKey", serviceKey)
										.queryParam("numOfRows", "10")
										.queryParam("base_date", "20210907")
										.queryParam("base_time", "0600")
										.queryParam("nx", "55")
										.queryParam("ny", "127")
										.queryParam("dataType", "JSON")
										.queryParam("pageNo", "1")
										.build(false); // 인코딩 false
				
				System.out.println("builder.toUriString() = " +  builder.toUriString());
				ObjectMapper objectMapper = new ObjectMapper();
				
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				ApiResponse_Total total = objectMapper.readValue(restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class).getBody(), ApiResponse_Total.class);
				JsonNode responseNode = objectMapper.readTree(restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class).getBody()).findPath("item");
				
				CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, ApiItem.class);
				List<ApiItem> ApiItemList =  objectMapper.readValue(responseNode.toString(), collectionType);
				
				/*
				 * for(ApiItem apiItem : ApiItemList) {
				 * System.out.println(apiItem.getObsrValue()); }
				 */
				
				System.out.println("ApiItemList = " + ApiItemList.toString());
				
				//System.out.println("responseNode" + responseNode.toString());
				//System.out.println("total.body.dataType = " + total.getResponse().getBody().getDataType());
				//System.out.println("total.getItems = " + total.getBody().getItems());
				//System.out.println("total.body.Items = " + responseNode.get);
				//System.out.println("ResultMsg = " + jsonParse.getResponse().getHeader().getResultCode());
				
				System.out.println("uriBuildResult= " + builder.toUriString());
				//restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
				System.out.println(restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class).getBody());
				
				
				//return builder.toUriString();
		} catch (Exception e) {
			e.getStackTrace();
		}
		
	        
	        /**
	        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
			
			RestTemplate restTemplate = applicationContext.getBean("restTemplate", RestTemplate.class);
			String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
			String serviceKey = null;
			try {
				 serviceKey = URLDecoder.decode("waSLmIW1sOFIRsbM4h70q0TF9c%2FtnyWEeeR3UI4W17a2H1HUt3Axc5o02pFUSiugbSXREvEa68kVjIPLTbjpRw%3D%3D", "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
					.queryParam("serviceKey", serviceKey)
					.queryParam("numOfRows", "10")
					.queryParam("base_date", "20210830")
					.queryParam("base_time", "0600")
					.queryParam("nx", "55")
					.queryParam("ny", "127")
					.queryParam("dataType", "JSON")
					.queryParam("pageNo", "1")
					.build(false);
			System.out.println("uriBuildResult= " + builder.toUriString());
			//String url =  "&ny=127&dataType=JSON";
			//restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
			System.out.println(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers), String.class));
			//return builder.toUriString();
			 */
	}
	
}
