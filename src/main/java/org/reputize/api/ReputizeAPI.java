package org.reputize.api;

import lombok.Getter;
import org.reputize.api.profile.impl.ReputizeProfile;
import org.reputize.api.request.type.RequestType;
import org.reputize.api.review.impl.ReputizeReview;

@Getter
public class ReputizeAPI {

    private final String token;

    public ReputizeAPI(final String token) {
        this.token = token;
    }

    public ReputizeProfile buildProfile(final String id, final RequestType requestType) {
        return new ReputizeProfile(this, id, requestType);
    }

    public ReputizeReview buildReview(final String id, final RequestType requestType) {
        return new ReputizeReview(this, id, requestType);
    }
}
