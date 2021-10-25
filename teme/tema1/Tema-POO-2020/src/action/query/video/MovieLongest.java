package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Movie;
import files.Show;
import java.util.ArrayList;

public class MovieLongest {
    private final ModifiableDB dataBase;
    private final ActionInputData action;

    public MovieLongest(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        longest();
    }

    private void longest() {
        // check filters
        Movie mv = new Movie();
        ArrayList<Show> suitableMovies = mv.checkFilters(dataBase, action);

        // sort movies based on duration and title
        Show.sortBasedOnDuration(suitableMovies, action.getSortType());

        output(suitableMovies, action, dataBase);
    }

    static void output(final ArrayList<Show> suitableMovies, final ActionInputData action,
                       final ModifiableDB dataBase) {
        SerialLongest.output(suitableMovies, action, dataBase);
    }
}
