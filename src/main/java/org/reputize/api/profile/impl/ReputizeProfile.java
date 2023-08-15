package org.reputize.api.profile.impl;

import org.reputize.api.ReputizeAPI;
import org.reputize.api.profile.Profile;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class ReputizeProfile implements Profile {

    private final ReputizeAPI api;
    private final String id;
    private final JSONObject profileData;

    public ReputizeProfile(final ReputizeAPI api, final String id) {
        this.api = api;
        this.id = id;
        this.profileData = this.fetchProfileData();
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public UUID getUUID() {
        String uuidStr = this.profileData.optString("id", null); // Use "id" instead of "uuid"
        return uuidStr != null ? UUID.fromString(uuidStr) : null;
    }

    @Override
    public String getTitle() {
        return this.profileData.optString("title", null);
    }

    @Override
    public String getUsername() {
        return this.profileData.optString("username", null);
    }

    @Override
    public String getAvatar() {
        return this.profileData.optString("avatar_url", null);
    }

    @Override
    public double getAverageRating() {
        return Double.parseDouble(this.profileData.optString("average_rating", "0"));
    }

    @Override
    public int getTotalReviews() {
        return this.profileData.optInt("total_reviews", 0);
    }

    @Override
    public double getTotalValue() {
        return this.profileData.optDouble("total_value", 0);
    }

    // Inside the fetchProfileData method
    private JSONObject fetchProfileData() {
        try {
            String url = "https://www.reputize.org/api/v1/user?id=" + id;
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
                JSONObject userObject = jsonObject.getJSONObject("user"); // Extract the "user" object

                return userObject;
            } else {
                System.out.println("Request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }
}
