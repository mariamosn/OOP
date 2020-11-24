package action.query.actor;

import fileio.ActionInputData;
import files.ModifiableDB;

public class ActorQuery {
    ModifiableDB dataBase;
    ActionInputData action;
    public ActorQuery(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
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
