package weather.toyproject.httpRequest;

import org.springframework.web.util.UriComponents;

public interface RequestFactory {
	
	public UriComponents UrlFactory(String url, String ServiceKey) throws Exception;

}
