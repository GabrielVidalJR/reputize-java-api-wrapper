package org.reputize.api.request.impl;

import org.json.JSONObject;
import org.reputize.api.ReputizeAPI;
import org.reputize.api.request.Request;
import org.reputize.api.request.type.RequestType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UsernameRequest implements Request {

    private final String category;
    private final ReputizeAPI api;

    public UsernameRequest(final String category, final ReputizeAPI api) {
        this.category = category;
        this.api = api;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.USERNAME;
    }

    @Override
    public JSONObject fetchData(final String value) {
        try {
            String url = BASE_URL + this.category + "?username=" + value;
            String token = this.api.getToken();

            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String jsonResponse = response.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject reviewObject = jsonObject.getJSONObject(this.category);

                return reviewObject;
            } else {
                System.out.println("Request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }
}
