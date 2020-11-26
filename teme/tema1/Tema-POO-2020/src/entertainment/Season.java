package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    /**
     * not in the original skel
     * additions start here
     */
    private double sumOfRatings = 0;
    private int numberOfRatings = 0;

    public double getSumOfRatings() {
        return sumOfRatings;
    }

    public void setSumOfRatings(final double sumOfRatings) {
        this.sumOfRatings = sumOfRatings;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(final int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    /**
     * Returns the rating of the current season
     * @return a double value
     */
    public double getRating() {
        if (numberOfRatings == 0) {
            return 0;
        } else {
            return sumOfRatings / numberOfRatings;
        }
    }
    /**
     * not in the original skel
     * additions end here
     */

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

