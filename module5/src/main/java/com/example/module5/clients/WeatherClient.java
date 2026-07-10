package com.example.module5.clients;

import com.example.module5.DTO.Weather.WeatherResponseDTO;

public interface WeatherClient {
    public WeatherResponseDTO getWeatherResponse(String city);
}
