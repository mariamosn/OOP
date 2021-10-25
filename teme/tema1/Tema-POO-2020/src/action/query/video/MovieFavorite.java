package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Movie;
import files.Show;
import java.util.ArrayList;

public class MovieFavorite {
    private final ModifiableDB dataBase;
    private final ActionInputData action;

    public MovieFavorite(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        favorite();
    }

    private void favorite() {
        // check filters
        Movie mv = new Movie();
        ArrayList<Show> suitableMovies = mv.checkFilters(dataBase, action);

        Show.calculateFavCnt(suitableMovies, dataBase);

        // sort movies
        Show.sortBasedOnFavCnt(suitableMovies, action.getSortType());

        MovieLongest.output(suitableMovies, action, dataBase);
    }
}
