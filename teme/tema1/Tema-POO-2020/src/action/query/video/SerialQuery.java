package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;

public class SerialQuery {
    ModifiableDB dataBase;
    ActionInputData action;
    public SerialQuery(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        if (action.getCriteria().equals("ratings")) {
            new SerialRating(dataBase, action);
        } else if (action.getCriteria().equals("favorite")) {
            new SerialFavorite(dataBase, action);
        } else if (action.getCriteria().equals("longest")) {
            new SerialLongest(dataBase, action);
        } else if (action.getCriteria().equals("most_viewed")) {
            new SerialViewed(dataBase, action);
        } else {
            System.out.println("Invalid criteria!");
        }
    }
}
