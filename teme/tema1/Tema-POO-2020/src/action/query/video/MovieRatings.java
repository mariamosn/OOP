package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Movie;
import files.Show;
import java.util.ArrayList;

public class MovieRatings {
    private final ModifiableDB dataBase;
    private final ActionInputData action;

    public MovieRatings(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        ratings();
    }

    private void ratings() {
        // check filters
        Movie mv = new Movie();
        ArrayList<Show> suitableMovies = mv.checkFilters(dataBase, action);

        // sort movies based on ratings
        SerialRating.output(suitableMovies, action, dataBase);
    }
}
