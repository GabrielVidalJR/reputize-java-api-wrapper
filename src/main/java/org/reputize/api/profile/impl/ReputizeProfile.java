package org.reputize.api.profile.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.reputize.api.ReputizeAPI;
import org.reputize.api.profile.Profile;
import org.reputize.api.request.impl.IDRequest;
import org.reputize.api.request.impl.UsernameRequest;
import org.reputize.api.request.type.RequestType;
import org.reputize.api.review.impl.ReputizeReview;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ReputizeProfile implements Profile {

    private final ReputizeAPI api;
    private final String id;
    private final RequestType requestType;
    private final JSONObject profileData;

    public ReputizeProfile(final ReputizeAPI api, final String id, final RequestType requestType) {
        this.api = api;
        this.id = id;
        this.requestType = requestType;

        if (this.requestType == RequestType.USERNAME) {
            this.profileData = new UsernameRequest("user", api).fetchData(id);
            return;
        }

        this.profileData = new IDRequest("user", api).fetchData(id);
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
    public List<ReputizeReview> getReviews() {
        final List<ReputizeReview> reviews = new LinkedList<>();

        JSONArray reviewsArray = this.profileData.optJSONArray("reviews");

        if (reviewsArray != null) {
            for (int i = 0; i < reviewsArray.length(); i++) {

                final JSONObject reviewObj = reviewsArray.getJSONObject(i);
                final String id = reviewObj.getString("id");

                reviews.add(this.api.buildReview(id, RequestType.ID));
            }
        }

        return reviews;
    }

    @Override
    public double getTotalValue() {
        return this.profileData.optDouble("total_value", 0);
    }
}
