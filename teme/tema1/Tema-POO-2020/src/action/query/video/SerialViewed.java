package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import files.User;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SerialViewed {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public SerialViewed(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        viewed();
    }

    private void viewed() {
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

        calculateViewCnt(suitableSerials);

        // sort serials
        for (int i = 0; i < suitableSerials.size() - 1; i++) {
            for (int j = i + 1; j < suitableSerials.size(); j++) {
                int ok = 0;
                if (action.getSortType().equals("asc")) {
                    if (suitableSerials.get(i).viewCnt > suitableSerials.get(j).viewCnt) {
                        ok = 1;
                    } else if (suitableSerials.get(i).viewCnt == suitableSerials.get(j).viewCnt
                    && suitableSerials.get(i).getTitle().compareTo(suitableSerials.get(j).getTitle()) > 0) {
                        ok = 1;
                    }
                } else {
                    if (suitableSerials.get(i).viewCnt < suitableSerials.get(j).viewCnt) {
                        ok = 1;
                    } else if (suitableSerials.get(i).viewCnt == suitableSerials.get(j).viewCnt
                            && suitableSerials.get(i).getTitle().compareTo(suitableSerials.get(j).getTitle()) < 0) {
                        ok = 1;
                    }
                }
                if (ok == 1) {
                    Serial aux = suitableSerials.get(i);
                    suitableSerials.set(i, suitableSerials.get(j));
                    suitableSerials.set(j, aux);
                }
            }
        }

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableSerials.size() && queryRes.size() < action.getNumber(); i++) {
            if (suitableSerials.get(i).viewCnt != 0) {
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

    private void calculateViewCnt(ArrayList<Serial> movies) {
        for (Serial mv
                : movies) {
            int cnt = 0;
            for (User user
                    : dataBase.getUsers()) {
                if(user.history.containsKey(mv.getTitle())) {
                    cnt += user.history.get(mv.getTitle());
                }
            }
            mv.viewCnt = cnt;
        }
    }
}
