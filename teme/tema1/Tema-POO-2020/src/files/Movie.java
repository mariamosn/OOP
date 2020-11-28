package files;

import fileio.ActionInputData;
import fileio.MovieInputData;
import java.util.ArrayList;

/**
 * Information about a movie
 */
public class Movie extends Show {
    /**
     * Duration in minutes of the movie
     */
    private int duration;
    private double sumOfRatings;
    private int numberOfRatings;

    public Movie(final MovieInputData movie) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(), movie.getGenres());
        this.duration = movie.getDuration();
        this.sumOfRatings = 0;
        this.numberOfRatings = 0;
    }
    public Movie() {
        this.duration = 0;
        this.sumOfRatings = 0;
        this.numberOfRatings = 0;
    }
    /**
     * Getter for the movie's duration
     */
    @Override
    public int getDuration() {
        return duration;
    }
    /**
     * Getter for the current sum of all the ratings
     */
    public double getSumOfRatings() {
        return sumOfRatings;
    }
    /**
     * Setter for the current sum of all the ratings
     * @param sumOfRatings = the updated sum
     */
    public void setSumOfRatings(final double sumOfRatings) {
        this.sumOfRatings = sumOfRatings;
    }
    /**
     * Getter for the current number of ratings
     */
    public int getNumberOfRatings() {
        return numberOfRatings;
    }
    /**
     * Setter for the current number of ratings
     * @param numberOfRatings = the updated number of ratings
     */
    public void setNumberOfRatings(final int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }
    /**
     * Returns the current rating of the movie
     */
    @Override
    public double getRating() {
        if (numberOfRatings == 0) {
            return 0;
        } else {
            return sumOfRatings / numberOfRatings;
        }
    }

    /**
     * Returns an array with all the movies that fit the provided filters
     * @param dataBase contains information about all the movies
     * @param action contains the filters
     */
    @Override
    public ArrayList<Show> checkFilters(final ModifiableDB dataBase, final ActionInputData action) {
        ArrayList<Show> suitableMovies = new ArrayList<>();
        for (Show mv
                : dataBase.getMovies()) {
            if (check(mv, action)) {
                suitableMovies.add(mv);
            }
        }
        return suitableMovies;
    }
}
