package action.query.actor;

import fileio.ActionInputData;
import files.ModifiableDB;

public class ActorQuery {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public ActorQuery(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        querySolver();
    }

    /**
     * The method checks the type of query the current one is
     * and decides what action will be executed next
     */
    private void querySolver() {
        if (action.getCriteria().equals("average")) {
            new ActorAverage(this.dataBase, action);
        } else if (action.getCriteria().equals("awards")) {
            new ActorAwards(this.dataBase, action);
        } else if (action.getCriteria().equals("filter_description")) {
            new ActorFilter(this.dataBase, action);
        } else {
            System.out.println("Invalid criteria!");
        }
    }
}
