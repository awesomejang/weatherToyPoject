package weather.toyproject.Weather.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiResponse {

	@JsonProperty("header")
	private ApiHeader header;
	@JsonProperty("body")
	private ApiBody body;
}
