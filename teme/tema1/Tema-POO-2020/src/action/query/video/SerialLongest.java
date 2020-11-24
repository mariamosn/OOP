package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SerialLongest {
    ModifiableDB dataBase;
    ActionInputData action;
    public SerialLongest(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        longest();
    }

    private void longest() {
        // sort serials based on duration and title
        for (int i = 0; i < dataBase.getSerials().size() - 1; i++) {
            for (int j = i + 1; j < dataBase.getSerials().size(); j++) {
                int ok = 0;
                if (action.getSortType().equals("asc")) {
                    if (dataBase.getSerials().get(i).getDuration() > dataBase.getSerials().get(j).getDuration()) {
                        ok = 1;
                    } else if (dataBase.getSerials().get(i).getDuration() == dataBase.getSerials().get(j).getDuration()
                    && dataBase.getSerials().get(i).getTitle().compareTo(dataBase.getSerials().get(j).getTitle()) > 0) {
                        ok = 1;
                    }
                } else {
                    if (dataBase.getSerials().get(i).getDuration() < dataBase.getSerials().get(j).getDuration()) {
                        ok = 1;
                    } else if (dataBase.getSerials().get(i).getDuration() == dataBase.getSerials().get(j).getDuration()
                            && dataBase.getSerials().get(i).getTitle().compareTo(dataBase.getSerials().get(j).getTitle()) < 0) {
                        ok = 1;
                    }
                }
                if (ok == 1){
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

