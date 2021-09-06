package weather.toyproject.Weather.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiItem {

	@JsonProperty("baseDate")
	private String baseData;
	@JsonProperty("baseTime")
	private String baseTime;
	@JsonProperty("category")
	private String category;
	@JsonProperty("nx")
	private String nx;
	@JsonProperty("ny")
	private String ny;
	@JsonProperty("obsrValue")
	private String obsrValue;
}
