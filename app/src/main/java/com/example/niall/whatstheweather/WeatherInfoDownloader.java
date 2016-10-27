package com.example.niall.whatstheweather;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherInfoDownloader extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
        return downloadJsonString(urls[0]);
    }

    private String downloadJsonString(String urlString) {

        try {
            HttpURLConnection urlConnection = createHttpUrlConnection(urlString);
            InputStreamReader reader = createInputStreamReaderFromUrlConnection(urlConnection);
            return readJsonStringFromInputStreamReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpURLConnection createHttpUrlConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        return (HttpURLConnection) url.openConnection();
    }

    private InputStreamReader createInputStreamReaderFromUrlConnection(HttpURLConnection urlConnection)
            throws IOException {
        InputStream in = urlConnection.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);

        return reader;
    }

    private String readJsonStringFromInputStreamReader(InputStreamReader reader) throws IOException {
        StringBuilder output = new StringBuilder();

        int data = reader.read();
        while (data != -1) {
            char nextChar = (char) data;
            output.append(nextChar);
            data = reader.read();
        }

        return output.toString();
    }
}
