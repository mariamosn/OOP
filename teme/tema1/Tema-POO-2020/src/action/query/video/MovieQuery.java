package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;

public class MovieQuery {
    private final ModifiableDB dataBase;
    private final ActionInputData action;

    public MovieQuery(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        delegate();
    }

    private void delegate() {
        switch (action.getCriteria()) {
            case "ratings" -> new MovieRatings(dataBase, action);
            case "favorite" -> new MovieFavorite(dataBase, action);
            case "longest" -> new MovieLongest(dataBase, action);
            case "most_viewed" -> new MovieViewed(dataBase, action);
            default -> System.out.println("Invalid criteria!");
        }
    }
}

