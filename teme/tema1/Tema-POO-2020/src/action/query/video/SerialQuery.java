package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;

public class SerialQuery {
    private final ModifiableDB dataBase;
    private final ActionInputData action;

    public SerialQuery(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        delegate();
    }

    private void delegate() {
        switch (action.getCriteria()) {
            case "ratings" -> new SerialRating(dataBase, action);
            case "favorite" -> new SerialFavorite(dataBase, action);
            case "longest" -> new SerialLongest(dataBase, action);
            case "most_viewed" -> new SerialViewed(dataBase, action);
            default -> System.out.println("Invalid criteria!");
        }
    }
}
