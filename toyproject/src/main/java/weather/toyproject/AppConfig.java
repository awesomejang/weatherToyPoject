package weather.toyproject;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import weather.toyproject.com.AuthUtil;
import weather.toyproject.com.file.FileMapper;
import weather.toyproject.com.file.FileUtil;
import weather.toyproject.com.file.FileVO;
import weather.toyproject.com.file.dao.FileRepository;
import weather.toyproject.com.file.service.FileService;

@Configuration
public class AppConfig {
	
	@Autowired
	private FileMapper fileMapper;
	
	@Bean(name = "ApiResource")
	public PropertiesFactoryBean propertiesFactoryBean() throws Exception {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		ClassPathResource classPathResource = new ClassPathResource("com/ApiRequestInfo.properties");
		propertiesFactoryBean.setLocation(classPathResource);
		return propertiesFactoryBean;
	}
	
	@Bean(name = "messageConfig")
	public PropertiesFactoryBean messagePropertiesFactoryBean() throws Exception {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		ClassPathResource classPathResource = new ClassPathResource("com/message.properties");
		propertiesFactoryBean.setLocation(classPathResource);
		return propertiesFactoryBean;
	}
	
	
	@Bean
	public RestTemplate restTemplate() throws Exception {
		//HttpClient
		HttpComponentsClientHttpRequestFactory httpRequestfactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestfactory.setReadTimeout(50000);
		httpRequestfactory.setConnectionRequestTimeout(3000);
		HttpClient httpClient = HttpClientBuilder.create()
								.setMaxConnTotal(50) //200
								.setMaxConnPerRoute(20)
								.build();
		httpRequestfactory.setHttpClient(httpClient);
		return new RestTemplate(httpRequestfactory);
	}
	
	@Bean
	public AuthUtil authUtil() {
		return new AuthUtil();
	}
	
	private FileRepository fileRepository() {
		return new FileRepository(fileMapper);
	}
	
	private FileService fileService() {
		return new FileService(fileRepository());
	}
	
	@Bean(name = "fileUtil")
	public FileUtil fileUtil() {
		return new FileUtil(new FileVO(), fileService());
	}
	
}
