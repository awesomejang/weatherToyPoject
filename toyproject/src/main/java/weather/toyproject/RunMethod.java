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
import java.util.HashMap;

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
import com.fasterxml.jackson.databind.ObjectMapper;

import weather.toyproject.Weather.header;

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
										.queryParam("base_date", "20210831")
										.queryParam("base_time", "0600")
										.queryParam("nx", "55")
										.queryParam("ny", "127")
										.queryParam("dataType", "JSON")
										.queryParam("pageNo", "1")
										.build(false); // 인코딩 false
				
				ObjectMapper objectMapper = new ObjectMapper();
				//objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				header Header =  objectMapper.readValue(restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class).getBody(), header.class);
				System.out.println("ResultMsg = " + Header.getResultMsg());
				System.out.println("ResultCode = " + Header.getResultCode());
				
				System.out.println("uriBuildResult= " + builder.toUriString());
				//restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
				System.out.println(restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class).getBody());
				
				
				//return builder.toUriString();
		} catch (Exception e) {
			// TODO: handle exception
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
