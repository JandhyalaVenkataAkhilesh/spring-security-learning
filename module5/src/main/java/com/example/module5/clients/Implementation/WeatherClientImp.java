package com.example.module5.clients.Implementation;

import com.example.module5.DTO.Weather.WeatherResponseDTO;
import com.example.module5.clients.WeatherClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
@Service
public class WeatherClientImp implements WeatherClient {
    private final RestClient restClient;
    @Value("${weather.api.key}")
    String WEATHER_API_KEY;
    public WeatherClientImp(@Qualifier("weatherRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public WeatherResponseDTO getWeatherResponse(String city) {
        return restClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("current.json")
                                .queryParam("key",WEATHER_API_KEY)
                                .queryParam("q",city)
                                .build()
                )
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,(request, response) -> {
                    throw new RuntimeException("Client Side error");
                })
                .body(WeatherResponseDTO.class);
    }
}
