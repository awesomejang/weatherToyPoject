package weather.toyproject.httpRequest;

import java.net.SocketTimeoutException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface RequestFactory {
	
	public ResponseEntity ApiRequestResult() throws Exception;
	
	public UriComponents uriComponentsBuilder();
	
	public List JsonToList(ResponseEntity<String> requestResult) throws Exception;
	
	public Object JsonToObject(ResponseEntity<String> requestResult) throws Exception;
}
