package com.example.niall.whatstheweather;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class TestWeatherInfoJsonConverter {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String exampleJsonString = "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}, {\"id\":805,\"main\":\"Rain\",\"description\":\"light drizzle\",\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":286.029,\"pressure\":1021.09,\"humidity\":71,\"temp_min\":286.029,\"temp_max\":286.029,\"sea_level\":1028.62,\"grnd_level\":1021.09},\"wind\":{\"speed\":3.16,\"deg\":36.5009},\"clouds\":{\"all\":88},\"dt\":1477146007,\"sys\":{\"message\":0.0035,\"country\":\"GB\",\"sunrise\":1477118288,\"sunset\":1477155040},\"id\":2643743,\"name\":\"London\",\"cod\":200}";

    @Test
    public void testConvertFromString() {
        String expectedCity = "London";
        String expectedDescription = "Clouds: overcast clouds\nRain: light drizzle";

        try {
            WeatherInfo testInfo = WeatherInfoJsonConverter.convertFromJsonString(exampleJsonString);
            assertEquals("Exected city was: " + expectedCity + " but was: " + testInfo.getCity(),
                    expectedCity, testInfo.getCity());
            assertEquals("Expected description was: " + expectedDescription
                            + " but was: " + testInfo.getDescription(),
                    expectedDescription, testInfo.getDescription());
        } catch(WeatherInfoDownloadException e) {
            e.printStackTrace();
            fail("Exception thrown when not expected!");
        }
    }

    @Test
    public void testConvertFromJsonObject() {
        String expectedCity = "London";
        String expectedDescription = "Clouds: overcast clouds\nRain: light drizzle";

        try {
            WeatherInfo testInfo = WeatherInfoJsonConverter
                    .convertFromJsonObject(new JSONObject(exampleJsonString));
            assertEquals("Exected city was: " + expectedCity + " but was: " + testInfo.getCity(),
                    expectedCity, testInfo.getCity());
            assertEquals("Expected description was: " + expectedDescription
                            + " but was: " + testInfo.getDescription(),
                    expectedDescription, testInfo.getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWeatherInfoDownloadExceptionThrownWhenJsonIsIncorrect()
            throws WeatherInfoDownloadException {
        thrown.expect(WeatherInfoDownloadException.class);
        thrown.expectMessage("Error downloading weather info!");

        WeatherInfo testInfo = WeatherInfoJsonConverter.convertFromJsonString("Wrong");
    }
}