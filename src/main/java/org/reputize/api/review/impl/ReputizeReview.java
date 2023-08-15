package org.reputize.api.review.impl;

import org.json.JSONObject;
import org.reputize.api.ReputizeAPI;
import org.reputize.api.profile.impl.ReputizeProfile;
import org.reputize.api.request.impl.IDRequest;
import org.reputize.api.request.type.RequestType;
import org.reputize.api.review.Review;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReputizeReview implements Review {

    private final ReputizeAPI api;
    private final String id;
    private RequestType requestType;
    private final JSONObject reviewData;

    public ReputizeReview(final ReputizeAPI api, final String id, final RequestType requestType) {
        this.api = api;
        this.id = id;
        this.requestType = requestType;

        if (this.requestType == RequestType.USERNAME) {
            this.reviewData = new IDRequest("review", api).fetchData(id);

            System.out.println("[ReputizeAPI] A review cannot be requested via username, automatically converted to ID.");
            return;
        }

        this.reviewData = new IDRequest("review", api).fetchData(id);
    }

    @Override
    public String getReviewID() {
        return this.id;
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
        return this.api.buildProfile(this.getSenderID(), RequestType.ID);
    }

    @Override
    public ReputizeProfile getReceiverProfile() {
        return this.api.buildProfile(this.getReceiverID(), RequestType.ID);
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
