package com.example.module5.DTO.Weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentDTO {

    @JsonProperty("temp_c")
    private Double tempC;

    private Integer humidity;

    private ConditionDTO condition;

}
