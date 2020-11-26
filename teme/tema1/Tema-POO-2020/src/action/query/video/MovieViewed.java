package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Movie;
import files.Show;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class MovieViewed {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public MovieViewed(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        viewed();
    }

    private void viewed() {
        // check filters
        Movie mv = new Movie();
        ArrayList<Show> suitableMovies = mv.checkFilters(dataBase, action);

        Show.calculateViewCnt(suitableMovies, dataBase);

        // sort movies
        Show.sortBasedOnViews(suitableMovies, action.getSortType());

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableMovies.size() && queryRes.size() < action.getNumber(); i++) {
            if (suitableMovies.get(i).getViewCnt() != 0) {
                queryRes.add(suitableMovies.get(i).getTitle());
            }
        }
        try {
            JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                    "Query result: " + queryRes);
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}
