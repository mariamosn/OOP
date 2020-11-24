package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Movie;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MovieLongest {
    ModifiableDB dataBase;
    ActionInputData action;
    public MovieLongest(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        longest();
    }

    private void longest() {
        // check filters
        ArrayList<Movie> suitableMovies = new ArrayList<>();
        for (Movie mv
                : dataBase.getMovies()) {
            Integer year = mv.getYear();
            int ok = 1;
            if (action.getFilters().get(0) != null
                    && action.getFilters().get(0).get(0) != null
                    && action.getFilters().get(0).get(0).compareTo(year.toString()) != 0) {
                ok = 0;
            } else if (action.getFilters().get(1) != null
                && action.getFilters().get(1).get(0) != null) {
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

        // sort movies based on duration and title
        for (int i = 0; i < suitableMovies.size() - 1; i++) {
            for (int j = i + 1; j < suitableMovies.size(); j++) {
                int ok = 0;
                if (action.getSortType().equals("asc")) {
                    if (suitableMovies.get(i).getDuration() > suitableMovies.get(j).getDuration()) {
                        ok = 1;
                    } else if (suitableMovies.get(i).getDuration() == suitableMovies.get(j).getDuration()
                        && suitableMovies.get(i).getTitle().compareTo(suitableMovies.get(j).getTitle()) > 0) {
                        ok = 1;
                    }
                } else {
                    if (suitableMovies.get(i).getDuration() < suitableMovies.get(j).getDuration()) {
                        ok = 1;
                    } else if (suitableMovies.get(i).getDuration() == suitableMovies.get(j).getDuration()
                            && suitableMovies.get(i).getTitle().compareTo(suitableMovies.get(j).getTitle()) < 0) {
                        ok = 1;
                    }
                }
                if (ok == 1){
                    Movie aux = suitableMovies.get(i);
                    suitableMovies.set(i, suitableMovies.get(j));
                    suitableMovies.set(j, aux);
                }
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
