package files;

import fileio.MovieInputData;
import fileio.ShowInput;

import java.util.ArrayList;

/**
 * Information about a movie
 */
public class Movie extends fileio.ShowInput {
    /**
     * Duration in minutes of a season
     */
    private int duration;
    public double sumOfRatings = 0;
    public int numberOfRatings = 0;
    public int favCnt = 0;
    public int viewCnt = 0;

    public Movie(MovieInputData movie) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(), movie.getGenres());
        this.duration = movie.getDuration();
    }

    public int getDuration() {
        return duration;
    }

    public double getRating() {
        if (numberOfRatings == 0) {
            return 0;
        } else {
            return sumOfRatings / numberOfRatings;
        }
    }

    public int getFavCnt() {
        return favCnt;
    }

    public void setFavCnt(int favCnt) {
        this.favCnt = favCnt;
    }

    @Override
    public String toString() {
        return "Movie{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
