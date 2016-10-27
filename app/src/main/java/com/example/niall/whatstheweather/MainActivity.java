package com.example.niall.whatstheweather;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WeatherInfoController controller;
    private EditText cityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText = (EditText) findViewById(R.id.cityEditText);
        controller = new WeatherInfoController();
    }

    public void getWeatherInfo(View view) {
        try {
            hideKeyboard();
            String cityName = ((TextView) findViewById(R.id.cityEditText)).getText().toString();
            String weatherDescription = controller.downloadCityWeatherInfo(cityName);
            updateWeatherDescription(weatherDescription);
        } catch (WeatherInfoDownloadException e) {
            String errorMessage = e.getMessage() + ": There seems to have been an error retrieving weather information. "
                    + "Please check your internet connection and the city entered is correct";
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyboard() {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityText.getWindowToken(), 0);
    }

    private void updateWeatherDescription(String description) {
        TextView weatherDescription = (TextView) findViewById(R.id.weatherInfoTextView);
        weatherDescription.setText(description);
    }
}
