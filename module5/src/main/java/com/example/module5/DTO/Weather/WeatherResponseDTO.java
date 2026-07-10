package com.example.module5.DTO.Weather;

import lombok.Data;

@Data
public class WeatherResponseDTO {

    private LocationDTO location;

    private CurrentDTO current;

}
