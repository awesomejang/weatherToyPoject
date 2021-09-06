package weather.toyproject.Weather.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiHeader {
	
	@JsonProperty("resultCode")
	private String resultCode;
	@JsonProperty("resultMsg")
	private String resultMsg;

}
