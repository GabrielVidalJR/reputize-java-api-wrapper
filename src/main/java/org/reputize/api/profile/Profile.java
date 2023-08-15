package org.reputize.api.profile;

import org.reputize.api.review.impl.ReputizeReview;

import java.util.List;
import java.util.UUID;

public interface Profile {

    /**
     * Gets the User's ID.
     *
     * @return {@link String} containing the ID.
     */

    String getID();

    /**
     * Converts the User's ID into UUID format.
     *
     * @return {@link UUID} of the user's ID.
     */

    UUID getUUID();

    /**
     * Get's the user's description
     *
     * @return {@link String} of the user's description.
     */

    String getTitle();

    /**
     * Get's the username of this user.
     *
     * @return {@link String} containing the user's name.
     */

    String getUsername();

    /**
     * Get's the URL of the user's avatar.
     *
     * @return {@link String} containing the user's avatar.
     */

    String getAvatar();

    /**
     * Get's the average ratings this user has received.
     *
     * @return {@link Double} containing average reviews.
     */

    double getAverageRating();

    /**
     *
     * @return {@link List<ReputizeReview>} containing reviews.
     */

    List<ReputizeReview> getReviews();

    /**
     * Get's the user's total value amongst all deals.
     *
     * @return {@link Double} containing value.
     */

    double getTotalValue();
}
