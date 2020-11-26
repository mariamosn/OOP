package action.query;

import action.query.actor.ActorQuery;
import action.query.user.UserQuery;
import action.query.video.MovieQuery;
import action.query.video.SerialQuery;
import fileio.ActionInputData;
import files.ModifiableDB;

public class Query {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public Query(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = new ModifiableDB(dataBase);
        this.action = action;
        if (action.getObjectType().equals("actors")) {
            new ActorQuery(this.dataBase, action);
        } else if (action.getObjectType().equals("movies")) {
            new MovieQuery(this.dataBase, action);
        } else if (action.getObjectType().equals("shows")) {
            new SerialQuery(this.dataBase, action);
        } else if (action.getObjectType().equals("users")) {
            new UserQuery(dataBase, action);
        } else {
            System.out.println("Invalid type of object!");
        }
    }
}
