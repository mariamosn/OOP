package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SerialRating {
    ModifiableDB dataBase;
    ActionInputData action;
    public SerialRating(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        ratings();
    }

    private void ratings() {
        // sort serials based on ratings
        for (int i = 0; i < dataBase.getSerials().size() - 1; i++) {
            for (int j = 0; j < dataBase.getSerials().size(); j++) {
                if ((action.getSortType().equals("asc")
                        && dataBase.getSerials().get(i).getRating() > dataBase.getSerials().get(j).getRating())
                        || (action.getSortType().equals("desc")
                        && dataBase.getSerials().get(i).getRating() < dataBase.getSerials().get(j).getRating())){
                    Serial aux = dataBase.getSerials().get(i);
                    dataBase.getSerials().set(i, dataBase.getSerials().get(j));
                    dataBase.getSerials().set(j, aux);
                }
            }
        }

        // check filters
        ArrayList<Serial> suitableSerials = new ArrayList<>();
        for (Serial mv
                : dataBase.getSerials()) {
            Integer year = mv.getYear();
            int ok = 1;
            if (mv.getRating() == 0) {
                ok = 0;
            }
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

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableSerials.size() && queryRes.size() < action.getNumber(); i++) {
            queryRes.add(suitableSerials.get(i).getTitle());
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
