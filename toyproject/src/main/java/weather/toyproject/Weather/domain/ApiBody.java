package weather.toyproject.Weather.domain;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ApiBody {

	@JsonProperty("dataType")
	private String dataType;
	@JsonProperty("items")
	private ApiBodyItems items;
}
