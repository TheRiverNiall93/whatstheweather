package com.example.niall.whatstheweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherInfoJsonConverter {
    private static final String NAME_KEY = "name";
    private static final String WEATHER_KEY = "weather";
    private static final String MAIN_KEY = "main";
    private static final String DESCRIPTION_KEY = "description";

    public static WeatherInfo convertFromJsonString(String jsonString) throws WeatherInfoDownloadException {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return convertFromJsonObject(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new WeatherInfoDownloadException("Error downloading weather info!");
        }
    }

    public static WeatherInfo convertFromJsonObject(JSONObject jsonObject) throws JSONException {
        String cityName = jsonObject.getString(NAME_KEY);
        String description = extractDescriptionData(jsonObject.getJSONArray(WEATHER_KEY));
        return new WeatherInfo(cityName, description);
    }

    private static String extractDescriptionData(JSONArray arr) throws JSONException {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonPart = arr.getJSONObject(i);
            output.append(jsonPart.getString(MAIN_KEY));
            output.append(": ");
            output.append(jsonPart.getString(DESCRIPTION_KEY));
            output.append("\n");
        }

        return output.toString().trim();
    }
}
