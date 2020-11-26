package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Movie;
import files.Show;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class MovieFavorite {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public MovieFavorite(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        favorite();
    }

    private void favorite() {
        // check filters
        Movie mv = new Movie();
        ArrayList<Show> suitableMovies = mv.checkFilters(dataBase, action);

        Show.calculateFavCnt(suitableMovies, dataBase);

        // sort movies
        Show.sortBasedOnFavCnt(suitableMovies, action.getSortType());

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableMovies.size() && queryRes.size() < action.getNumber(); i++) {
            queryRes.add(suitableMovies.get(i).getTitle());
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
