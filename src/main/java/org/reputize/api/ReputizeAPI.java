package org.reputize.api;

import lombok.Getter;
import org.reputize.api.profile.impl.ReputizeProfile;
import org.reputize.api.review.impl.ReputizeReview;

@Getter
public class ReputizeAPI {

    private final String token;

    public ReputizeAPI(final String token) {
        this.token = token;
    }

    public ReputizeProfile buildProfile(final String id) {
        return new ReputizeProfile(this, id);
    }

    public ReputizeReview buildReview(final String id) {
        return new ReputizeReview(this, id);
    }
}
