package com.example.niall.whatstheweather;

public class WeatherInfo {
    private String city;
    private String description;

    public WeatherInfo(String city, String description) {
        this.city = city;
        this.description = description;
    }

    public String getCity() {
        return this.city;
    }

    public String getDescription() {
        return this.description;
    }
}
