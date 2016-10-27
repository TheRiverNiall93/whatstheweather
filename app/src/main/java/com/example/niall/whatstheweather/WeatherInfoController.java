package com.example.niall.whatstheweather;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class WeatherInfoController {
    private final String OPENWEATHERMAP_APP_ID = "&APPID=3bd0c49861aaf0b5ef05336799fb8193";
    private final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    private WeatherInfo weatherInfo;

    public String downloadCityWeatherInfo(String cityName) throws WeatherInfoDownloadException {
        try {
            WeatherInfoDownloader weatherTask = new WeatherInfoDownloader();
            String encodedCityName = URLEncoder.encode(cityName, "UTF-8");
            String jsonString = weatherTask.execute(API_URL
                    + encodedCityName + OPENWEATHERMAP_APP_ID).get();

            if (jsonString == null) {
                throw new WeatherInfoDownloadException("Error downloading weather info!");
            }

            weatherInfo = WeatherInfoJsonConverter.convertFromJsonString(jsonString);

            return weatherInfo.getDescription();
        } catch (InterruptedException|ExecutionException|UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new WeatherInfoDownloadException("Error downloading weather info!");
        }
    }
}
