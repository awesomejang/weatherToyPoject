package weather.toyproject.Weather.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiResponse_Total {

	@JsonProperty("response")
	private ApiResponse response;
	@JsonProperty("body")
	private ApiBody body;
}
