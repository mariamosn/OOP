package action.query;

import action.query.actor.ActorQuery;
import action.query.user.UserQuery;
import action.query.video.MovieQuery;
import action.query.video.SerialQuery;
import fileio.ActionInputData;
import files.ModifiableDB;

public class Query {
    private final ModifiableDB dataBase;
    private final ActionInputData action;

    public Query(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = new ModifiableDB(dataBase);
        this.action = action;
        delegate();
    }

    /**
     * The method checks the type of query the current one is
     * and dictates the next actions that need to be done.
     */
    private void delegate() {
        switch (action.getObjectType()) {
            case "actors" -> new ActorQuery(this.dataBase, action);
            case "movies" -> new MovieQuery(this.dataBase, action);
            case "shows" -> new SerialQuery(this.dataBase, action);
            case "users" -> new UserQuery(dataBase, action);
            default -> System.out.println("Invalid type of object!");
        }
    }
}
