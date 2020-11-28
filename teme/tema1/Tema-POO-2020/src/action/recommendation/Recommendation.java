package action.recommendation;

import fileio.ActionInputData;
import files.ModifiableDB;

public class Recommendation {
    private ModifiableDB dataBase;
    private ActionInputData action;

    public Recommendation(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = new ModifiableDB(dataBase);
        this.action = action;
        recommend();
    }

    /**
     * The method checks the type of recommendation the current one is
     * and dictates the next actions that need to be done.
     */
    private void recommend() {
        if (action.getType().equals("standard")) {
            new StandardRecommendation(dataBase, action);
        } else if (action.getType().equals("best_unseen")) {
            new BestRecommendation(dataBase, action);
        } else if (action.getType().equals("popular")) {
            new PopularRecommendation(dataBase, action);
        } else if (action.getType().equals("favorite")) {
            new FavoriteRecommendation(dataBase, action);
        } else if (action.getType().equals("search")) {
            new SearchRecommendation(dataBase, action);
        } else {
            System.out.println("Invalid type of recommendation!");
        }
    }
}
