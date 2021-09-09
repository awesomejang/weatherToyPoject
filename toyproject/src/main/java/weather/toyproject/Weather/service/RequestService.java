package weather.toyproject.Weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import weather.toyproject.httpRequest.RequestFactory;

@Service
//@PropertySource("classpath:/com/ApiRequestInfo.properties")
public class RequestService implements RequestFactory {
	
	//@Value("key.api")
	@Value("#{ApiResource['key.api']}")
	private String url;

	@Override
	public UriComponents UrlFactory(String url, String ServiceKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
