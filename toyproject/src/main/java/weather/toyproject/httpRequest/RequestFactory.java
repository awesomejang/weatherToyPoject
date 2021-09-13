package weather.toyproject.httpRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;

public interface RequestFactory {
	
	public ResponseEntity ApiRequestResult() throws Exception;
	
	public UriComponents uriComponentsBuilder() throws Exception;

}
