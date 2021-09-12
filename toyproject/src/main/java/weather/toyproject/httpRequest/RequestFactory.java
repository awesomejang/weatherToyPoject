package weather.toyproject.httpRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponents;

public interface RequestFactory {
	
	public String ApiRequestResult() throws Exception;
	
	public UriComponents uriComponentsBuilder() throws Exception;

}
