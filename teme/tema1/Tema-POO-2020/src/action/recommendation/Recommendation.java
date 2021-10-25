package action.recommendation;

import fileio.ActionInputData;
import files.ModifiableDB;

public class Recommendation {
    private final ModifiableDB dataBase;
    private final ActionInputData action;

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
        switch (action.getType()) {
            case "standard" -> new StandardRecommendation(dataBase, action);
            case "best_unseen" -> new BestRecommendation(dataBase, action);
            case "popular" -> new PopularRecommendation(dataBase, action);
            case "favorite" -> new FavoriteRecommendation(dataBase, action);
            case "search" -> new SearchRecommendation(dataBase, action);
            default -> System.out.println("Invalid type of recommendation!");
        }
    }
}
