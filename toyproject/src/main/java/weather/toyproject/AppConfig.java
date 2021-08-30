package weather.toyproject;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		//HttpClient
		HttpComponentsClientHttpRequestFactory httpRequestfactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestfactory.setReadTimeout(5000);
		httpRequestfactory.setConnectionRequestTimeout(3000);
		HttpClient httpClient = HttpClientBuilder.create()
								.setMaxConnTotal(200)
								.setMaxConnPerRoute(20)
								.build();
		httpRequestfactory.setHttpClient(httpClient);
		return new RestTemplate(httpRequestfactory);
	}
}
