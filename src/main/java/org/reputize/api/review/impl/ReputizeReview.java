package org.reputize.api.review.impl;

import org.reputize.api.profile.impl.ReputizeProfile;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.reputize.api.ReputizeAPI;
import org.reputize.api.review.Review;

public class ReputizeReview implements Review {

    private final ReputizeAPI api;
    private final String id;
    private final JSONObject reviewData;

    public ReputizeReview(final ReputizeAPI api, final String id) {
        this.api = api;
        this.id = id;
        this.reviewData = this.fetchReviewData();
    }

    @Override
    public String getMessage() {
        return this.reviewData.optString("message", null);
    }

    @Override
    public String getDescription() {
        return this.reviewData.optString("description", null);
    }

    @Override
    public Double getRating() {
        return this.reviewData.optDouble("rating", 0);
    }

    @Override
    public String getSenderID() {
        return this.reviewData.optString("sender", null);
    }

    @Override
    public String getReceiverID() {
        return this.reviewData.optString("receiver", null);
    }

    @Override
    public Double getValue() {
        return this.reviewData.optDouble("value", 0);
    }

    @Override
    public Date getDate() {
        String dateString = this.reviewData.optString("time_sent", null);
        return parseDate(dateString);
    }

    @Override
    public String getPlatform() {
        return this.reviewData.optString("platform", null);
    }

    @Override
    public ReputizeProfile getSenderProfile() {
        return this.api.buildProfile(this.getSenderID());
    }

    @Override
    public ReputizeProfile getReceiverProfile() {
        return this.api.buildProfile(this.getReceiverID());
    }

    private JSONObject fetchReviewData() {
        try {
            String url = "https://www.reputize.org/api/v1/review?id=" + id;
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
                JSONObject reviewObject = jsonObject.getJSONObject("review");

                return reviewObject;
            } else {
                System.out.println("Request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }

    private Date parseDate(String dateString) {
        if (dateString != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
