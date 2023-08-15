package org.reputize.api.review;

import org.reputize.api.profile.impl.ReputizeProfile;

import java.util.Date;

/**
 * Represents a review.
 */
public interface Review {

    /**
     * Gets the review ID.
     *
     * @return {@link String} The Review ID.
     */

    String getReviewID();

    /**
     * Gets the review message.
     *
     * @return {@link String} The review message.
     */
    String getMessage();

    /**
     * Gets the review description.
     *
     * @return {@link String} The review description.
     */
    String getDescription();

    /**
     * Gets the review rating.
     *
     * @return {@link Double} The review rating.
     */
    Double getRating();

    /**
     * Gets the ID of the sender.
     *
     * @return {@link String} The ID of the sender.
     */
    String getSenderID();

    /**
     * Gets the ID of the receiver.
     *
     * @return {@link String} The ID of the receiver.
     */
    String getReceiverID();

    /**
     * Gets the review value.
     *
     * @return {@link Double} The review value.
     */
    Double getValue();

    /**
     * Gets the review date.
     *
     * @return {@link Date} The review date.
     */
    Date getDate();

    /**
     * Gets the platform of the review.
     *
     * @return {@link String} The platform of the review.
     */
    String getPlatform();

    /**
     * Gets the profile of the sender.
     *
     * @return {@link ReputizeProfile} The sender's profile
     */

    ReputizeProfile getSenderProfile();

    /**
     * Gets the profile of the receiver.
     *
     * @return {@link ReputizeProfile} The receiver's profile.
     */

    ReputizeProfile getReceiverProfile();
}
