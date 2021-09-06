package weather.toyproject.Weather.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiBodyItems {

	@JsonProperty("item")
	private List<ApiItem> item;
}
