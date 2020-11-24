package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import files.User;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SerialFavorite {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public SerialFavorite(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        favorite();
    }

    private void favorite() {
        // check filters
        ArrayList<Serial> suitableSerials = new ArrayList<>();
        for (Serial mv
                : dataBase.getSerials()) {
            Integer year = mv.getYear();
            int ok = 1;
            if (action.getFilters().get(0) != null
                    && action.getFilters().get(0).get(0) != null
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
                suitableSerials.add(mv);
            }
        }

        calculateFavCnt(suitableSerials);

        // sort shows
        for (int i = 0; i < suitableSerials.size() - 1; i++) {
            for (int j = 0; j < suitableSerials.size(); j++) {
                if ((action.getSortType().equals("asc")
                        && suitableSerials.get(i).favCnt > suitableSerials.get(j).favCnt)
                        || (action.getSortType().equals("desc")
                        && suitableSerials.get(i).favCnt < suitableSerials.get(j).favCnt)) {
                    Serial aux = suitableSerials.get(i);
                    suitableSerials.set(i, suitableSerials.get(j));
                    suitableSerials.set(j, aux);
                }
            }
        }

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableSerials.size() && queryRes.size() < action.getNumber(); i++) {
            if (suitableSerials.get(i).favCnt != 0) {
                queryRes.add(suitableSerials.get(i).getTitle());
            }
        }
        try {
            JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                    "Query result: " + queryRes);
            dataBase.arrayResult.add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    private void calculateFavCnt(ArrayList<Serial> Serials) {
        for (Serial mv
                : Serials) {
            int cnt = 0;
            for (User user
                    : dataBase.getUsers()) {
                if(user.getFavoriteMovies().contains(mv.getTitle())) {
                    cnt++;
                }
            }
            mv.favCnt = cnt;
        }
    }
}
