package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Movie;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MovieRatings {
    ModifiableDB dataBase;
    ActionInputData action;
    public MovieRatings(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        ratings();
    }

    private void ratings() {
        // sort movies based on ratings
        for (int i = 0; i < dataBase.getMovies().size() - 1; i++) {
            for (int j = 0; j < dataBase.getMovies().size(); j++) {
                if ((action.getSortType().equals("asc")
                && dataBase.getMovies().get(i).getRating() > dataBase.getMovies().get(j).getRating())
                || (action.getSortType().equals("desc")
                        && dataBase.getMovies().get(i).getRating() < dataBase.getMovies().get(j).getRating())){
                    Movie aux = dataBase.getMovies().get(i);
                    dataBase.getMovies().set(i, dataBase.getMovies().get(j));
                    dataBase.getMovies().set(j, aux);
                }
            }
        }

        // check filters
        ArrayList<Movie> suitableMovies = new ArrayList<>();
        for (Movie mv
                : dataBase.getMovies()) {
            Integer year = mv.getYear();
            int ok = 1;
            if (mv.getRating() == 0) {
                ok = 0;
            }
            if (action.getFilters().get(0) != null
            && action.getFilters().get(0).get(0).compareTo(year.toString()) != 0) {
                ok = 0;
            } else if (action.getFilters().get(1) != null) {
                for (String genere
                        : action.getFilters().get(1)) {
                    if (!mv.getGenres().contains(genere)) {
                        ok = 0;
                    }
                }
            }

            if (ok == 1) {
                suitableMovies.add(mv);
            }
        }

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableMovies.size() && queryRes.size() < action.getNumber(); i++) {
            queryRes.add(suitableMovies.get(i).getTitle());
        }
        try {
            JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                    "Query result: " + queryRes);
            dataBase.arrayResult.add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}
