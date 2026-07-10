package com.example.module5.DTO.Weather;

import lombok.Data;

@Data
public class LocationDTO {

    private String name;

    private String region;

    private String country;

    private Double lat;

    private Double lon;

}
